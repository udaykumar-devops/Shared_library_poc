def call() {
    emailext (
    mimeType: 'text/html',
    subject: "Pipeline Build  ${env.BUILD_NUMBER} - ${currentBuild.result} (${currentBuild.fullDisplayName})",
    body: """
      <br>BUILD_STATUS: ${currentBuild.result}
      <br>JOB_NAME: ${env.JOB_NAME}
      <br>BUILD_NUMBER: ${env.BUILD_NUMBER}
      <br>BRANCH_NAME: ${env.BRANCH_NAME}
      <br>BUILD_URL: ${env.BUILD_URL}console
    """,
    to: recipientList,
    recipientProviders: [requestor(), developers(), culprits(), upstreamDevelopers(), brokenTestsSuspects(), brokenBuildSuspects()],
    from: "no-reply@jenkins.com",
    compressLog: false,
    attachLog: false
  )
}