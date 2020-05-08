pipeline {
    agent {
        label 'JAVA'
    }
    environment {
        SONAR_TOKEN     = credentials('SonarToken-StudentApp')
        NEXUS           = credentials('NEXUS')
    }
    stages {
        stage('Application release auto version') {
            steps {
                git credentialsId: 'Git-User', url: 'https://github.com/naveenthangella/student.git'
                sh '''
                    mvn -s settings.xml release:prepare -B
                    mvn -s settings.xml release:perform -DNEXUS_USR=${NEXUS_USR} -DNEXUS_PSW=${NEXUS_PSW} 
                '''
            }
        }
    }
    post {
        success{
            slackSend color: 'blue', message: "SUCCESSFULLY RELEASED WITH AUTO VERSION"
        }
        failure {
            slackSend color: 'red', message: "BUILD FAILED - CHECK URL :: ${BUILD_URL}"
        }
    }

}