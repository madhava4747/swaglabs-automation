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
                sh '''
                if exist target\\allure-report rmdir /s /q target\\allure-report
                if exist target\\allure-results rmdir /s /q target\\allure-results
                mvn test
                allure generate target/allure-results --clean -o target/allure-report
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-report/**', fingerprint: true
            echo 'Allure report archived successfully'
        }
        success {
            echo 'Build SUCCESS'
        }
        failure {
            echo 'Build FAILED'
        }
    }
}
