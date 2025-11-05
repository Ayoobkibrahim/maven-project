@Library('jenkins-shared-library') _

pipeline{
    agent {label 'local-agent'}

    parameters{
        string(name: 'BUILD_NUMBER', defaultValue: '', description:'Build number from Build Job')
        string(name: 'IMAGE_TAG', defaultValue: '', description: 'Image tag to deploy')
        string(name: 'NAMESPACE', defaultValue: 'default', description: 'Kubernetes namespace')
        string(name: 'RELEASE_NAME', defaultValue: 'maven-app', description: 'Helm release name')
    }

    environment {
        DOCKERHUB_CREDENTIALS = 'docker-jenkins-token'
        DOCKERHUB_USERNAME = 'ayoobki'
        HELM_CHART_REPO = "docker.io/${DOCKERHUB_USERNAME}/maven-app"  
    }

    stages{

        stage('Checkout'){
            steps{
                git url: 'https://github.com/Ayoobkibrahim/maven-project.git', branch: 'main'
            }
        }


        stage('Download Helm Chart from Docker Hub'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId:"${DOCKERHUB_CREDENTIALS}",usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]){
                        sh """
                        echo "\$DOCKER_PASS" | helm registry login -u "\$DOCKER_USER" --password-stdin docker.io

                        helm pull oci://docker.io/\$DOCKER_USER/maven-app --version 0.1.0

                        tar -xzf maven-app-*.tgz
                        """
                    }

                    def chartDir = sh(
                        script: "tar -tzf maven-app-*.tgz | head -1 | cut -d'/' -f1",
                        returnStdout: true
                    ).trim()
                    env.CHART_DIR = chartDir

                    echo "📦 Chart directory identified: ${env.CHART_DIR}"
                    echo "✅ Helm chart downloaded successfully.."
                }
            }
        }


        stage('Deploy to Kubernetes'){
            steps{
                script{
                    echo "🚀 Deploying application to Kubernetes..."

                    sh """

                    if [! -d "${env.CHART_DIR}"]; then
                        echo "❌ Error: Chart directory ${env.CHART_DIR} not found!"
                        ls -la
                        exit 1
                    fi

                    sed -i 's|tag:.*|tag: \"${params.IMAGE_TAG}\"|g' ${env.CHART_DIR}/values.yaml

                    helm upgrade --install ${params.RELEASE_NAME} ./${env.CHART_DIR} \
                            --namespace ${params.NAMESPACE} \
                            --create-namespace \
                            --set image.tag=${params.IMAGE_TAG} \
                            --wait \
                            --timeout 5m

                    """

                    echo "✅ Application deployed successfully"
                }
            }
        }


        stage('Verify Deployment'){
            steps{
                script{
                    echo "🔍 Verifying deployment..."

                    sh """
                    kubectl get pods -n ${params.NAMESPACE} -l app.kubernetes.io/name=maven-app
                    kubectl get svc -n ${params.NAMESPACE} -l app.kubernetes.io/name=maven-app
                    kubectl get ingress -n ${params.NAMESPACE} -l app.kubernetes.io/name=maven-app
                    """

                    echo "✅ Deployment verification complete"
                }
            }
        }

    }

    post {
        success {
            notify('SUCCESS')
        }
        failure {
            notify('FAILURE')
        }
    }


}