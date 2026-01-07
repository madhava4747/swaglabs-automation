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
                bat 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Generate Allure Report') {
            steps {
                bat '''
                if exist target\\allure-report rmdir /s /q target\\allure-report
                if exist target\\allure-results (
                    allure generate target\\allure-results --clean -o target\\allure-report
                ) else (
                    echo "No Allure results found"
                )
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-report/**', fingerprint: true
            echo 'Pipeline completed'
        }
        success {
            echo 'Build SUCCESS'
        }
        failure {
            echo 'Build FAILED'
        }
    }
}
