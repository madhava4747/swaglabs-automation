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
    }

    post {
        always {
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
