pipelineJob('sample-pipeline') {

    description('sample pipeline seed job ')

    displayName('sample-pipeline')

    configure { flowdefinition ->
        flowdefinition << delegate.'definition'(class: 'org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition', plugin: 'workflow-cps@2.80') {

            'scm'(class: 'hudson.plugins.git.GitSCM', plugin: 'git') {

                'userRemoteConfigs' {

                    'hudson.plugins.git.UserRemoteConfig' {

                        'url'('https://github.com/naveenthangella/Jenkins.git')

                    }

                }

                'branches' {

                    'hudson.plugins.git.BranchSpec' {

                        'name'('*/master')

                    }

                }

            }

            'scriptPath'('pipeline-jobs/sample.Jenkinsfile')

            'lightweight'(true)

        }
    }
}