@Library('my-shared-library@shared-library') _
 
pipeline {
    agent any
    stages {
        stage('Git Checkout') {
            steps {
            gitCheckout(
                branch: "shared-library",
                url: "ssh://git@github.com/gingeririna/test-maven-project.git"
            )
            }
    }
    }
}
