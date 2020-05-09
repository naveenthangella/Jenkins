pipeline {
    agent {
        label 'JAVA'
    }
    environment {
        CENTOS=credentials('CENTOS-KEY')
    }

    stages {
        stage('Create Green Environment') {
            steps {
                git credentialsId: 'Git-User', url: 'https://github.com/naveenthangella/Terraform.git'
                ansiColor('xterm') {
                    sh '''
                        export AWS_DEFAULT_REGION=us-west-2 
                        cd Project/studentapp/final
                        make prod-apply-bg
                    '''
                }
            }
        }

        stage('Pull list of Servers') {
            steps {
                git credentialsId: 'Git-User', url: 'https://github.com/naveenthangella/Ansible.git'
                sh '''
                    aws ec2 describe-instances --filters "Name=tag:DEPLOYMENT,Values=green"  --query 'Reservations[*].Instances[*].PrivateIpAddress' --output text >/tmp/prod-hosts-green
                '''
            }
        }

        stage('Do Deployment') {
            steps {
                sh '''
                    cat $CENTOS >pem
                    chmod 600 pem
                    ansible-playbook -i /tmp/prod-hosts-green --private-key=./pem Playbooks/deploy.yml -e RELEASE_VERSION=${RELEASE_VERSION}
                '''
            }
        }
    }

}
