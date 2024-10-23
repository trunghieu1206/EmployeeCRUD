pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // try-catch block needs to be placed in a script{}
                script{
                    try{
                        sh '''
                            echo "cleaning previous build"
                            docker compose down
                        '''
                    } catch(Exception e){
                        echo "previous build does not exist"
                    }
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
                    // values must be from configuration settings
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
        stage('Clean up') {
            steps {
                sh '''
                    echo "Cleaning up"
                    docker compose down
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