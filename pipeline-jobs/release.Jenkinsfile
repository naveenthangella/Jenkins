pipeline {
    agent {
        label 'JAVA'
    }
    environment {
        SONAR_TOKEN     = credentials('SonarToken-StudentApp')
        NEXUS           = credentials('NEXUS')
    }
    stages {
        stage('Clone Terraform Repo') {
            steps {
                git credentialsId: 'Git-User', url: 'https://github.com/naveenthangella/Terraform.git'
            }
        }

        stage('Create Test Infra') {
            steps {
                sh '''
                    cd project-test-infra
                    terraform init 
                    terraform apply -auto-approve
                '''
            }
        }

        stage('Deploy DEV Snapshot') {
            steps {
                copyArtifacts filter: 'url.txt', projectName: 'CI-pipleine'
                sh '''
                    URL=$(cat url.txt)
                    cd project-test-infra
                    cat deploy/deploy.tf >>provider.tf
                    terraform init 
                    terraform apply -auto-approve -var URL=${URL}
                '''
            }
        }
    }
    post {
        success{
            slackSend color: 'blue', message: "BUILD SUCCESS"
        }
        failure {
            slackSend color: 'red', message: "BUILD FAILED - CHECK URL :: ${BUILD_URL}"
        }
    }

}
