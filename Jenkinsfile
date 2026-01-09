
pipeline {
    agent any

    tools {
        maven 'Maven-LOCAL'
        jdk   'JDK-Local'
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
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
                bat '''
                mvn clean test ^
                -Dsurefire.suiteXmlFiles=testng.xml
                '''
            }
        }

        // Optional: quick folder check if needed
        stage('Verify Allure Folder') {
            steps {
                bat 'dir target'
                bat 'dir target\\allure-results'
            }
        }
    }

    post {
        always {
            echo 'Publishing Allure report'
            script {
                allure(
                    includeProperties: false,
                    jdk: '',
                    results: [[
                        path: 'target/allure-results',
                        reportBuildPolicy: 'ALWAYS'
                    ]]
                )
            }
        }
        success { echo '✅ Tests executed successfully' }
        failure { echo '❌ Test execution failed' }
    }
}
