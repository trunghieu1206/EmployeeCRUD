pipeline {
    agent any

    environment {
        JMETER_FOLDER = 'jmeter-tests'
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
                    pwd
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

                    mkdir ${JMETER_FOLDER}/report

                    echo "Performing performance test with Jmeter"
                    jmeter -n -t ${JMETER_FOLDER}/performance_test_spring_boot_app.jmx -l ${JMETER_FOLDER}/performance_test_spring_boot_app_result.jtl -e -o ${JMETER_FOLDER}/report -j ${JMETER_FOLDER}/jmeter.log

                    cat ${JMETER_FOLDER}/performance_test_spring_boot_app_result.jtl
                '''
            }
        }
        stage('Logging'){
            steps{
                // archive results of performance test
                archiveArtifacts artifacts: "${JMETER_FOLDER}/performance_test_spring_boot_app_result.jtl", allowEmptyArchive: true
                archiveArtifacts artifacts: "${JMETER_FOLDER}/report/*.html", allowEmptyArchive: true

                sh "ls -l ${JMETER_FOLDER}/report/index.html"

                // This command will publish JMeter's HTML reports to Jenkins
                publishHTML(target: [
                    reportName: "Performance test result", // Specifies the name of the report to be displayed in Jenkins
                    reportDir: "${JMETER_FOLDER}/report", // The directory where the JMeter HTML reports are stored
                    reportFiles: "index.html", // Specifies which HTML file(s) to use as the report (typically index.html in JMeter)
                    keepAll: true, // If set to true, all reports from previous builds will be kept, and users can view reports from past builds
                    alwaysLinkToLastBuild: true // When set to true, Jenkins will always link to the latest build’s report
                ])
            }
        }
    }
    post{
        success{
            echo "pipeline succeeded"
            sh '''
                cd jmeter-tests/report/
                pwd
            '''
        }
        failure{
            echo "pipeline failed"
        }
        always{
            sh '''
                echo "Cleaning up"
                docker compose down
                mvn clean
            '''
        }
    }
}