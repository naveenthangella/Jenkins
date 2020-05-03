pipeline {
    agent {
        label 'JAVA'
    }
    environment {
        SONAR_TOKEN     = credentials('SonarToken-StudentApp')
        NEXUS           = credentials('NEXUS')
    }
    stages {
        stage('Clone the Repository') {
            steps {
                git 'https://github.com/naveenthangella/student.git'
            }
        }
        stage('Compile the code') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('SonarQube Scan') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh ' mvn sonar:sonar -Dsonar.projectKey=CI-pipeline -Dsonar.host.url=http://sonar.naveenthangella.ga:9000 -Dsonar.login=${SONAR_TOKEN}'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Upload artifacts to NEXUS') {
            steps {
                sh ' mvn package deploy -DNEXUS_USR=${NEXUS_USR} -DNEXUS_PSW=${NEXUS_PSW}'
            }
        }

    }
    post {
        success{
            slackSend color: 'red', message: "BUILD SUCCESS"
        }
        failure {
            slackSend color: 'red', message: "BUILD FAILED - CHECK URL :: ${BUILD_URL}"
        }
    }

}