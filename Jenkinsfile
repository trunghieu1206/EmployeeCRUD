pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean"
                sh '''
                    echo "building using docker compose"
                    docker compose build
                '''
            }
        }
        stage('Vulnerability Scan'){
            steps{
                echo "Scanning using Snyk"
                snykSecurity(
                    snykInstallation: 'snyk@latest',
                    snykTokenId: 'snyk-api-token'
                )
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    echo "Deploying using docker compose"
                    docker compose up -d
                '''
            }
        }
        stage('Cleanup'){
            steps{
                echo "cleaning up"
                sh "docker compose down "
            }
        }
    }
    post{
        success{
            echo "pipeline completed successfully"
        }
        failure{
            echo "pipeline failed"
        }
    }
}