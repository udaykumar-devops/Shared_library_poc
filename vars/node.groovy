def call(Map pipelineParams) {
    pipeline {
        agent none
        environment {
            COMPLIANCEENABLED = true
        }
        options {
            skipDefaultCheckout()
            buildDiscarder(logRotator(artifactDaysToKeepStr: '5', artifactNumToKeepStr: '5', daysToKeepStr: '30', numToKeepStr: '5'))
            skipStagesAfterUnstable() // Stop pipeline at unstable
        }
        stages {
            stage('checkout & build') {
                agent {
                    docker {
                        image 'node:16.15.1'
                        label 'dind'
                    }
                }
                steps {
                    deleteDir()
                    sh """
                        checkout scm
                        npm install
                        ng build --prod --aot --sm --progress=false
                        npm install -g bestzip
                        bestzip ../demo-app.zip *
                        ls -ltra
                    """
                }
            }
            // stage('upload to S3'){
            //     steps {
            //         deploy(pipelineParams.developmentServer, pipelineParams.serverPort)
            //     }
            // }
        } // stages
        post {
            success{
                script {
                    echo "Your pipeline was successful sending notification...."
                    notify()
                }
            }
            failure{
                script {
                    echo "Your pipeline failed sending notification...."
                    notify()
                }
            }
        }
    }
}
