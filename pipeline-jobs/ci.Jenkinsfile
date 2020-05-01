pipeline {
    agent {
        label 'JAVA'
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
    }
}