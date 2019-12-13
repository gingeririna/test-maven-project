@Library('jenkins-library@master') _
 
pipeline {
    agent any
    stages {
        stage('Git Checkout') {
            steps {
            gitCheckout(
                branch: "master",
                url: "ssh://git@github.com/gingeririna/test-maven-project.git",
                credid: "68bf5876-4e9c-4327-8572-3484ab24dd74"
            )
            }
        }
        stage('build') {
            steps {
                script {
                    def conf = readYaml file: 'config.yml'
                    env.folder = conf.build.projectFolder
                    env.script = conf.build.buildCommand
                }
                deploy(
                    foldername: "${folder}",
                    script: "${script}"
                )
            } 
        }    
        stage('database') {
            steps {
                script {
                    def conf = readYaml file: 'config.yml'
                    env.folder = conf.database.databaseFolder
                    env.script = conf.database.databaseCommand
                }
                deploy(
                    foldername: "${folder}",
                    script: "${script}"
                )
            } 
        }
        stage('deploy') {
            steps {
                script {
                    def conf = readYaml file: 'config.yml'
                    env.folder = conf.build.projectFolder
                    env.script = conf.deploy.deployCommand
                }
                deploy(
                    foldername: "${folder}",
                    script: "${script}"
                )
            } 
        }    
        stage ('tests') {
            parallel {
                stage ('integration') {
                    steps {
                        script {
                            def conf = readYaml file: 'config.yml'
                            for (int i = 0; i < conf.test.size(); ++i) {
                                if ( conf.test[i].name == 'integration') {
                                    def testdata = conf.test[i]
                                    env.folder = testdata.testFolder
                                    env.script = testdata.testCommand
                                    echo "${testdata}"
                                }
                            }
                        }
                        test (
                            foldername: "${folder}",
                            script: "${script}"
                        )
                    }
                }    
                stage ('performance') {
                    steps {
                        script {
                            def conf = readYaml file: 'config.yml'
                            for (int i = 0; i < conf.test.size(); ++i) {
                                if ( conf.test[i].name == 'performance') {
                                    def testdata = conf.test[i]
                                    env.folder2 = testdata.testFolder
                                    env.script2 = testdata.testCommand
                                    echo "${testdata}"
                                }
                            }
                        }
                        test (
                            foldername: "${folder2}",
                            script: "${script2}"
                        )
                    }
                }    
                stage ('regression') {
                    steps {
                        script {
                            def conf = readYaml file: 'config.yml'
                            for (int i = 0; i < conf.test.size(); ++i) {
                                if ( conf.test[i].name == 'regression') {
                                    def testdata = conf.test[i]
                                    env.folder3 = testdata.testFolder
                                    env.script3 = testdata.testCommand
                                    echo "${testdata}"
                                }
                            }
                        }
                        test (
                            foldername: "${folder3}",
                            script: "${script3}"
                        )
                    }
                }    
            }
        }
    } 
    post {
        failure {
            mail to: 'team@example.com', subject: 'Pipeline failed', body: "${env.BUILD_URL}"
        }
        success {
            mail to: 'team@example.com', subject: 'Pipeline succeeded', body: "${env.BUILD_URL}"
        }
    }
}
