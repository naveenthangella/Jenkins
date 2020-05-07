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
                ansiColor('xterm') {
                    sh '''
                        cd project-test-infra
                        terraform init 
                        terraform apply -auto-approve
                    '''
                }
            }
        }

        stage('Deploy DEV Snapshot') {
            steps {
                copyArtifacts filter: 'url.txt', projectName: 'CI-pipeline'
                ansiColor('xterm') {
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

        stage('Selenium Tests') {
            steps {
                git credentialsId: 'Git-User', url: 'https://github.com/naveenthangella/selenium.git'
                ansiColor('xterm') {
                    sh '''
                       IP=$(cat /tmp/ip.txt)
                       sed -i -e "s/IPADDRESS/$IP/"  src/test/java/framework/CrudStudent.java
                       mvn clean install "-Dremote=true" "-DseleniumGridURL=http://selenium.naveenthangella.ga:4444/wd/hub" "-Dbrowser=Chrome" "-Doverwrite.binaries=true"
                    '''
                }
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