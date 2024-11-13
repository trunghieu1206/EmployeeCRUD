pipeline {
    agent any

    environment {
        JMETER_FOLDER = './jmeter-tests'
    }

    stages {
//         stage('Unit Test'){
//             steps{
//                 echo "Performing unit tests"
//                 sh '''
//                     mvn clean test
//                 '''
//             }
//         }
//         stage('Integration Test'){
//             steps{
//                 echo "Performing integration tests"
//                 sh '''
//                     mvn verify -Dskip.ut=true
//                 '''
//             }
//         }
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
        stage('Performance Test'){
            steps{
                sh '''
                    rm -rf ${JMETER_FOLDER}/performance_test_spring_boot_app_result.jtl
                    rm -rf ${JMETER_FOLDER}/report

                    echo "Performing performance test with Jmeter"
                    jmeter -n -t ${JMETER_FOLDER}/performance_test_spring_boot_app.jmx -l ${JMETER_FOLDER}/performance_test_spring_boot_app_result.jtl -e -o ${JMETER_FOLDER}/report
                '''
            }
        }
        stage('Logging'){
            steps{
                echo "Reports on Performance test generated"
                // archive results of performance test
                archiveArtifacts artifacts: '${JMETER_FOLDER}/performance_test_spring_boot_app_result.jtl', allowEmptyArchive: true
                archiveArtifacts artifacts: '${JMETER_FOLDER}/report/*.html', allowEmptyArchive: true

                // Publish the JMeter test results as visual reports
                junit '${JMETER_FOLDER}/performance_test_spring_boot_app_result.jtl'
                // This command will publish JMeter's HTML reports to Jenkins
                publishHTML(target: [
                    reportName: 'Performance test result', // Specifies the name of the report to be displayed in Jenkins
                    reportDir: '${JMETER_FOLDER}/result', // The directory where the JMeter HTML reports are stored
                    reportFiles: 'index.html', // Specifies which HTML file(s) to use as the report (typically index.html in JMeter)
                    keepAll: true, // If set to true, all reports from previous builds will be kept, and users can view reports from past builds
                    alwaysLinkToLastBuild: true // When set to true, Jenkins will always link to the latest build’s report
                ])
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
            echo "pipeline succeeded"
        }
        failure{
            echo "pipeline failed"
        }
    }
}