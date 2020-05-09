pipeline {
    agent {
        label 'JAVA'
    }

    stages {
        stage('Pull list of servers') {
            steps {
                git credentialsId: 'Git-User', url: 'https://github.com/naveenthangella/Ansible.git'
                sh '''
                    aws ec2 describe-instances --filters "Name=tag:DEPLOYMENT,Values=blue"  --query 'Reservations[*].Instances[*].PrivateIpAddress' --output text >/tmp/prod-hosts 
                '''
            }
        }
        stage('Do Deployment') {
            steps {
                sh '''
                    ansible-playbook -i /tmp/prod-hosts Playbooks/deploy.yml -e RELEASE_VERSION=${RELEASE_VERSION}
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