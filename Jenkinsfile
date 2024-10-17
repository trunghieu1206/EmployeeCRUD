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
                sh '''
                    echo "cleaning previous build"
                    docker compose down
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