pipeline {
    agent {
        label 'JAVA'
    }
    environment {
        SONAR_TOKEN     = credentials('SonarToken-StudentApp')
    }
    stages{
        stage ('Clone the Repository') {
            steps {
                git 'https://github.com/naveenthangella/student.git'
            }
        }
        stage ('Compile the code') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('SonarQube Scan'){
            steps {
                sh ' mvn sonar:sonar -Dsonar.projectKey=CI-pipeline -Dsonar.host.url=http://sonar.naveenthangella.ga:9000 -Dsonar.login=${SONAR_TOKEN}'
            }
        }
    }
}