pipeline {
    agent any

    environment {
        SONAR_LOGIN='admin'
        SONAR_PASSWORD='Hieuhieuhieu1!'
        SONAR_PROJECT_VERSION=''
    }

    stages {
        stage('Clean and Compile'){
            steps{
                echo "Compile source files"
                sh '''
                    ./mvnw clean compile
                '''
            }
        }
        stage('Unit Test'){
            steps{
                echo "Performing unit tests"
                sh '''
                    ./mvnw test
                '''
            }
        }
        stage('Static code analysis'){
            steps{
                // we have to use double quotes so that we can substitute environment variables
                // these are either global variables provided by Jenkins or specified in environment{} block
                sh """
                    /Users/hieuhoang/Desktop/sonarqube/sonar-scanner-6.2.1.4610-macosx-aarch64/bin/sonar-scanner \
                        -Dsonar.projectKey=${JOB_BASE_NAME} \
                        -Dsonar.projectName=${JOB_NAME} \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src \
                        -Dsonar.login=${SONAR_LOGIN} \
                        -Dsonar.password=${SONAR_PASSWORD} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.sourceEncoding=UTF-8
                """
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
        always{
            sh '''
                echo "Cleaning up"
                ./mvnw clean
            '''
        }
    }
}