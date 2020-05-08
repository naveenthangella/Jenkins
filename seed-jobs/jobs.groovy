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
        name('RELEASE-pipeline')
        name('MANUAL-release')
        name('AUTO-release')
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
    description('pipeline to clone a git repos and generate war file and deploy the artifact to nexus')
    displayName('CI-pipeline')

   // triggers {
   //     scm('H/2 * * * *'){
   //         ignorePostCommitHooks()
   //     }
   // }

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

pipelineJob('RELEASE-pipeline') {
    description('pipeline to create the test infra and deploy the the application into test infra')
    displayName('Release-pipeline')
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
            'scriptPath'('pipeline-jobs/release.Jenkinsfile')
            'lightweight'(true)
        }
    }
}

pipelineJob('MANUAL-release') {
    description('')
    displayName('MANUAL-release')
    parameters {
        stringParam('RELEASE_VERSION', '','RELEASE VERSION OF APPLICATION')
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
            'scriptPath'('pipeline-jobs/manual-release.Jenkinsfile')
            'lightweight'(true)
        }
    }
}

pipelineJob('AUTO-release') {
    description('')
    displayName('AUTO-release')
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
            'scriptPath'('pipeline-jobs/auto-release.Jenkinsfile')
            'lightweight'(true)
        }
    }
}