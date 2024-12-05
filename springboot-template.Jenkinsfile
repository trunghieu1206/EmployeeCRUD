pipeline {
    agent any

    environment {
        DSONAR_PROJECT_KEY=''
        DSONAR_PROJECT_NAME=''
        DSONAR_PROJECT_VERSION=''
        DSONAR_LOGIN=''
        DSONAR_PASSWORD=''
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
                    env.DSONAR_LOGIN = env.SONAR_LOGIN
                    env.DSONAR_PASSWORD = env.SONAR_PASSWORD
                }
                sh "DSONAR_PROJECT_NAME: ${env.DSONAR_PROJECT_NAME}"
                sh "DSONAR_PROJECT_VERSION: ${env.DSONAR_PROJECT_VERSION}"
                sh "DSONAR_LOGIN: ${env.DSONAR_LOGIN}"
                sh "DSONAR_PASSWORD: ${env.DSONAR_PASSWORD}"
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
                // we have to use double quotes so that we can substitute environment variables
                sh """
                    /Users/hieuhoang/Desktop/sonarqube/sonar-scanner-6.2.1.4610-macosx-aarch64/bin/sonar-scanner \
                        -Dsonar.projectKey=${JOB_BASE_NAME} \
                        -Dsonar.projectName=${JOB_NAME} \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src \
                        -Dsonar.login=${DSONAR_LOGIN} \
                        -Dsonar.password=${DSONAR_PASSWORD} \
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