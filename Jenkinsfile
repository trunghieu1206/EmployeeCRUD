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
                try{
                    sh '''
                        echo "cleaning previous build"
                        docker compose down
                    '''
                } catch(Exception e){
                    echo "previous build does not exist"
                }
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