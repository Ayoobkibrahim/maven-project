@Library('jenkins-shared-library') _

pipeline{
    agent {label 'local-agent'}

    parameters{
        string(name: 'BUILD_NUMBER', defaultValue: '', description:'Build number from Build Job')
        string(name: 'IMAGE_TAG', defaultValue: '', description: 'Image tag to deploy')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'stage', 'prod'], description: 'Select deployment environment')
    }

    environment {

        ARTIFACTORY_URL = 'http://localhost:8081'
        ARTIFACTORY_REPO = 'kustomize-artifacts'
        ARTIFACTORY_CRED = 'nexus-credentials'
    }

    stages{

        stage('Download Kustomize Package'){
            steps{
                echo "Downloading Kustomize package from Artifactory..."

                withCredentials([usernamePassword(credentialsId: "${ARTIFACTORY_CRED}", usernameVariable: 'ART_USER', passwordVariable: 'ART_PASS')]){
                    sh """
                        curl -u $ART_USER:$ART_PASS \
                        -O "${ARTIFACTORY_URL}/repository/${ARTIFACTORY_REPO}/kustomize-${BUILD_NUMBER}.tar.gz"
                    """
                }
                
            }
        }

        stage('Extract Kustomize Package'){
            steps{
                echo "Extracting Kustomize package..."

                sh "tar -xzf kustomize-${BUILD_NUMBER}.tar.gz"
            }
        }


        stage('Deploy to Kubernetes'){
            steps{
                script{
                    echo "Deploying to ${params.ENVIRONMENT} environment..."

                    sh """
                        cd maven-kustomize/overlays/${params.ENVIRONMENT}
                        /home/jenkins-agent/kustomize edit set image ayoobki/maven-app:${IMAGE_TAG}
                    """

                    withCredentials([file(credentialsId: 'kubernetes-kubeconfig', variable: 'KUBECONFIG_FILE')]){
                        sh """
                            export KUBECONFIG=$KUBECONFIG_FILE

                            kubectl get ns ${params.ENVIRONMENT} || kubectl create ns ${params.ENVIRONMENT}
                            kubectl apply -k maven-kustomize/overlays/${params.ENVIRONMENT} --namespace=${params.ENVIRONMENT}
                        """

                    }
                }
            }
        }

        stage('Verify Deployment'){
            steps{
                script{
                    echo "Verifying deployment..."

                    withCredentials([file(credentialsId: 'kubernetes-kubeconfig', variable: 'KUBECONFIG_FILE')]){
                                   
                    sh """
                    set -e

                    export KUBECONFIG=\"\$KUBECONFIG_FILE\"

                    kubectl get pods -n ${params.ENVIRONMENT}
                    kubectl get svc -n ${params.ENVIRONMENT}
                    kubectl get ingress -n ${params.ENVIRONMENT} 
                    """
                    }

                    echo "Deployment verification complete"
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