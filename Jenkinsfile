pipeline {
    agent any

    stages {
        stage('Unit Test'){
            steps{
                echo "Performing unit tests"
                sh '''
                    mvn clean test
                '''
            }
        }
        stage('Integration Test'){
            steps{
                echo "Performing integration tests"
                sh '''
                    mvn verify -Dskip.ut=true
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
        stage('Staging') {
            steps {
                sh '''
                    echo "Deploying on Docker"
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