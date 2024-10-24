pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test'){
            steps{
                echo "Testing"
                sh '''
                    mvn test
                '''
            }
        }
//         stage('Vulnerability Scan'){
//             steps{
//                 echo "Scanning using Snyk"
//                 snykSecurity(
//                     // values must be from configuration settings
//                     snykInstallation: 'snyk@latest',
//                     snykTokenId: 'snyk-api-token'
//                 )
//             }
//         }
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
                    mvn clean
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