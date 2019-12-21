@Library('jenkins-library@master') _
 
def conf


pipeline {
    agent any
    stages {
        stage('Read properties') {
            steps {
                script {
                    conf = readYaml file: 'config.yml'
                    recipients = conf.notifications.email.recipients
                }
            }
        }
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
                    //def conf = readYaml file: 'config.yml'
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
                    //def conf = readYaml file: 'config.yml'
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
                    //def conf = readYaml file: 'config.yml'
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
            steps {
                script {

                    def tests = [:]
                    
                    for (int i = 0; i < conf.test.size(); ++i) {
                        def name = conf.test[i].name
                        def folder = conf.test[i].testFolder
                        def command = conf.test[i].testCommand
                        tests["${conf.test[i].name}"] = {
                            stage("${name}") {
                                echo "i'm in"
                                echo "${name}"
                                test(
                                    foldername: "${folder}",
                                    script: "${command}"
                                )
                                echo "I'm out"
                            }
                        }
                    }   
                    parallel tests
                }
            }
        } 
    }
    post {
        failure {
            mail to: "${recipients}", subject: 'Pipeline failed', body: "${env.BUILD_URL}"
        }
        success {
            mail to: "${recipients}", subject: 'Pipeline succeeded', body: "${env.BUILD_URL}"
        }
    }
}
