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
                deploy(
                    foldername: "project",
                    script: "mvn clean test"
                )
            } 
        }    
        stage('database') {
            steps {
                deploy(
                    foldername: "database",
                    script: "mvn clean test -Dscope=FlywayMigration"
                )
            } 
        }
        stage('deploy') {
            steps {
                deploy(
                    foldername: "project",
                    script: "mvn clean install"
                )
            } 
        }    
        stage ('tests') {
            parallel {
                stage ('integration') {
                    steps {
                        test (
                            foldername: "test",
                            script: "mvn clean test -Dscope=integration"
                        )
                    }
                }
                stage ('performance') {
                    steps {
                        test (
                            foldername: "test",
                            script: "mvn clean test -Dscope=performance"
                        )
                    }
                }
                stage ('regression') {
                    steps {
                        test (
                            foldername: "test",
                            script: "mvn clean test -Dscope=regression; exit 1"
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


