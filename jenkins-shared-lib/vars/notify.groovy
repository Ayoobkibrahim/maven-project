def call(String buildStatus = 'SUCCESS') {
    def subject = ""
    def body = ""
    def emoji = ""

    switch(buildStatus) {
        case 'SUCCESS':
            emoji = "✅"
            subject = "${emoji} SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
            body = """Good news! The pipeline succeeded.

            • Job: ${env.JOB_NAME}
            • Build Number: ${env.BUILD_NUMBER}
            • URL: ${env.BUILD_URL}
            """
            break

        case 'FAILURE':
            emoji = "❌"
            subject = "${emoji} FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
            body = """The pipeline failed.

            • Job: ${env.JOB_NAME}
            • Build URL: ${env.BUILD_URL}
            • Check console logs for error details.
            """
            break

        case 'ABORTED':
            emoji = "🚫"
            subject = "${emoji} ABORTED: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
            body = """The build was manually aborted.

            • Job: ${env.JOB_NAME}
            • URL: ${env.BUILD_URL}
            • Check if it was canceled intentionally.
            """
            break

        default:
            emoji = "⚠️"
            subject = "${emoji} UNKNOWN STATUS: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
            body = """Pipeline ended with unknown status: ${buildStatus}.
            • Job: ${env.JOB_NAME}
            • URL: ${env.BUILD_URL}
            """
    }

    mail to: 'ayoobkibrahim109@gmail.com',
         subject: subject,
         body: body
}
