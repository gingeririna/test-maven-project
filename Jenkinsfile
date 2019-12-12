pipeline {
    agent any
    environment {
        branch = 'master'
        scmUrl = 'ssh://git@github.com/gingeririna/test-maven-project.git'
	def config = readYaml file: 'config.yml'
    }
    stages {
        stage('checkout git') {
            steps {
                git branch: branch, credentialsId: '68bf5876-4e9c-4327-8572-3484ab24dd74', url: scmUrl
            }
        }

        stage('build') {
            steps {
                //sh 'cd ./project && mvn clean test'
		dir : 'project'
		sh 'mvn clean test'
            }
        }

        stage('database'){
            steps {
                sh 'cd ./database && mvn clean test -Dscope=FlywayMigration' 
            }
        }

        stage('deploy'){
            steps {
		sh 'cd ./database && mvn clean install'
            }
        }

        stage ('test') {
            steps {
                parallel (
                    "integration tests": { sh 'cd ./test && mvn clean test -Dscope=integration' },
                    "performance tests": { sh 'cd ./test && mvn clean test -Dscope=performance'},
                    "regression tests": { sh 'cd ./test && mvn clean test -Dscope=regression; exit 1' }
                )
            }
        }
    }

    post {
        failure {
            mail to: config.notifications.email.recipients, subject: 'Pipeline failed', body: "${env.BUILD_URL}"
        }
        success {
            mail to: 'team@example.com', subject: 'Pipeline succeeded', body: "${env.BUILD_URL}"
        }
    }
}

