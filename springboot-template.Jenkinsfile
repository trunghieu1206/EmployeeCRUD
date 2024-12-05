pipeline {
    agent any

    environment {
        DSONAR_PROJECT_KEY=''
        DSONAR_PROJECT_NAME=''
        DSONAR_PROJECT_VERSION=''
        DSONAR_LOGIN="${env.SONAR_LOGIN}"
        DSONAR_PASSWORD="${env.SONAR_PASSWORD}"
    }

    stages {
        stage('Init environment variables'){
            steps{
                script{
                    env.DSONAR_PROJECT_NAME = sh(script: '''
                        git rev-parse --show-toplevel | sed 's/.*\\/\\(.*\\)$/\\1/'
                    ''', returnStdout: true).trim()
                    env.DSONAR_PROJECT_VERSION = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
                    env.DSONAR_PROJECT_KEY = "org.sonarqube:${env.SONAR_PROJECT_NAME}-${env.SONAR_PROJECT_VERSION}"
                }
            }
        }
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


                sh '''
                    /Users/hieuhoang/Desktop/sonarqube/sonar-scanner-6.2.1.4610-macosx-aarch64/bin/sonar-scanner \
                        -Dsonar.projectKey=${JOB_BASE_NAME} \
                        -Dsonar.projectName=${JOB_NAME} \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src \
                        -Dsonar.login=${SONAR_LOGIN} \
                        -Dsonar.password=${SONAR_PASSWORD} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.sourceEncoding=UTF-8
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
        always{
            sh '''
                echo "Cleaning up"
                ./mvnw clean
            '''
        }
    }
}