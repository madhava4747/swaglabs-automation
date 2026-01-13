pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    tools {
        maven 'Maven-LOCAL'
        jdk 'JDK-Local'
    }

    triggers {
        pollSCM('H/5 * * * *')
    }

    parameters {
        choice(name: 'ENV', choices: ['qa', 'dev', 'prod'])
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'])
        choice(name: 'SUITE', choices: ['smoke', 'regression'])
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Tests') {
            steps {
                bat """
                mvn clean test ^
                -Denv=${params.ENV} ^
                -Dbrowser=${params.BROWSER} ^
                -Dsuite=${params.SUITE} ^
                -Dbrowser.mode=headless
                """
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
            echo '✅ Build Passed'
        }
        failure {
            echo '❌ Build Failed'
        }
        always {
            echo '📊 Pipeline finished'
        }
    }
}
