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

        stage('Run Tests') {
            steps {
                sh '''
                    mvn clean test \
                    -DsuiteXmlFile=testng.xml
                '''
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh '''
                    allure generate target/allure-results \
                    -o target/allure-report --clean
                '''
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/allure-report/**', fingerprint: true
            echo 'Pipeline finished'
        }
    }
}
