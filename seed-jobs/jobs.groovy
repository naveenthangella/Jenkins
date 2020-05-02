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

listView('student') {
    description('All student jobs')
    filterBuildQueue()
    filterExecutors()
    jobs {
        name('CI-pipeline')
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}

pipelineJob('CI-pipeline') {
    description('pipeline to clone a git repos and generate war file ')
    displayName('CI-pipeline')

    triggers {
        scm('H/2 * * * *'){
            ignorePostCommitHooks()
        }
    }

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
            'scriptPath'('pipeline-jobs/ci.Jenkinsfile')
            'lightweight'(true)
        }
    }
}