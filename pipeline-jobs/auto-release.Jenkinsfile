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
                    sed -i -e "s/WORD/${RELEASE_VERSION}/" WebContent/index.jsp
                    mvn versions:set -DnewVersion=${RELEASE_VERSION}-RELEASE
                    mvn -s settings.xml package deploy -DNEXUS_USR=${NEXUS_USR} -DNEXUS_PSW=${NEXUS_PSW} 
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