pipeline {
    agent any

    options {
        timestamps()
    }

    tools {
        maven 'Maven-LOCAL'
        jdk 'JDK-Local'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
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
    }

    post {
        always {
            allure([
                results: [[path: 'target/allure-results']]
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
