pipeline {
    agent {
        label 'JAVA'
    }
    environment{
        CENTOS=credentials('CENTOS-KEY')
    }

    stages {
        stage('Pull list of servers') {
            steps {
                git credentialsId: 'Git-User', url: 'https://github.com/naveenthangella/Ansible.git'
                sh '''
                    export AWS_DEFAULT_REGION=us-west-2
                    aws ec2 describe-instances --filters "Name=tag:ENV,Values=PROD"  --query 'Reservations[*].Instances[*].PrivateIpAddress' --output text >/tmp/prod-hosts 
                '''
            }
        }
        stage('Do Deployment') {
            steps {
                sh '''
                    export ANSIBLE_HOST_KEY_CHECKING=false
                    cat $CENTOS >pem
                    chmod 600 pem
                    ansible-playbook -i /tmp/prod-hosts --private-key=./pem Playbooks/deploy.yml -e RELEASE_VERSION=${RELEASE_VERSION}
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