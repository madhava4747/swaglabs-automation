pipeline {
    agent any

    tools {
        maven 'Maven-LOCAL'
        jdk 'JDK-Local'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/madhava4747/swaglabs-automation.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Clean & Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh 'allure generate target/allure-results --clean -o target/allure-report'
            }
        }
    }

    post {
        always {
            publishHTML([
                reportDir: 'target/allure-report',
                reportFiles: 'index.html',
                reportName: 'Allure Test Report',
                allowMissing: false,          // ✅ REQUIRED FIX
                keepAll: true,
                alwaysLinkToLastBuild: true
            ])
            echo 'Pipeline execution finished'
        }
        success {
            echo 'Build SUCCESS'
        }
        failure {
            echo 'Build FAILED'
        }
    }
}
