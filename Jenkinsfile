pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Unit Test'){
            steps{
                echo "Performing unit tests"
                sh '''
                    mvn test
                '''
            }
        }
        stage('Performance Test') {
            steps{
                echo "Performing Spike tests"
                sh '''
                    jmeter -n -t ./jmeter-tests/spike_test.jmx -l ./jmeter-tests/spike_test_result.csv
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