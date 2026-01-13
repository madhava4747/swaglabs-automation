pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    parameters {
        choice(name: 'ENV', choices: ['qa','stage'])
        choice(name: 'BROWSER', choices: ['chrome','edge'])
        choice(name: 'SUITE', choices: ['smoke','regression'])
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                bat "mvn clean test -Denv=${params.ENV} -Dbrowser=${params.BROWSER} -Dsuite=${params.SUITE}"
            }
        }

        stage('Allure Report') {
            steps {
                allure results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        success {
            echo '✅ Build Success'
        }
        failure {
            echo '❌ Build Failed'
        }
    }
}
