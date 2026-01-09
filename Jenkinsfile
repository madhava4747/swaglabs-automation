
pipeline {
    agent any

    tools {
        maven 'Maven-LOCAL'
        jdk 'JDK-Local'
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
                bat 'mvn test -DsuiteXmlFile=testng.xml'
            }
        }
    }

    post {
        always {
            allure results: [[path: 'target/allure-results']]
        }
    }
}
