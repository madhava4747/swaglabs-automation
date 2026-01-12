pipeline {
    agent any

    tools {
        maven 'Maven-LOCAL'
        jdk 'JDK-Local'
    }

    parameters {
        choice(name: 'ENV', choices: ['qa', 'dev', 'prod'], description: 'Select Environment')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Select Browser')
        choice(name: 'SUITE', choices: ['smoke', 'regression'], description: 'Select Test Suite')
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/madhava4747/swaglabs-automation.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Run Tests') {
            steps {
                echo "ENV=${params.ENV}, BROWSER=${params.BROWSER}, SUITE=${params.SUITE}"

                bat """
                mvn clean test ^
                -Denv=${params.ENV} ^
                -Dbrowser=${params.BROWSER} ^
                -Dsuite=${params.SUITE}
                """
            }
        }

        stage('Generate Allure Report') {
            steps {
                bat '''
                if exist target\\allure-report rmdir /s /q target\\allure-report
                allure generate target\\allure-results --clean -o target\\allure-report
                '''
            }
        }
    }

    post {
        always {
            script {
                if (fileExists('target/allure-report/index.html')) {
                    publishHTML(target: [
                        reportName : 'Allure Report',
                        reportDir  : 'target/allure-report',
                        reportFiles: 'index.html',
                        keepAll    : true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: false
                    ])
                } else {
                    echo 'Allure report not found'
                }
            }
        }
    }
}
