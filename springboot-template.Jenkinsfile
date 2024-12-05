pipeline {
    agent any

    environment {
        SONAR_PROJECT_KEY=''
        SONAR_PROJECT_NAME=''
        SONAR_PROJECT_VERSION=''
        SONAR_LOGIN=''
        SONAR_PASSWORD=''
    }

    stages {
        stage('Init environment variables'){
            script{
                env.SONAR_PROJECT_NAME = sh(script: '''
                    git rev-parse --show-toplevel | sed 's/.*\\/\\(.*\\)$/\\1/'
                    ''', returnStdout: true).trim()
                env.SONAR_LOGIN = sh(script: "echo $SONAR_LOGIN", returnStdout: true).trim()
                env.SONAR_PASSWORD = sh(script: "echo $SONAR_PASSWORD", returnStdout: true).trim()
                env.SONAR_PROJECT_VERSION = sh(script: "git rev-parse HEAD", returnStdout: true).trim()
                env.SONAR_PROJECT_KEY = "org.sonarqube:${env.SONAR_PROJECT_NAME}-${env.SONAR_PROJECT_VERSION}"
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
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.projectName=${SONAR_PROJECT_NAME} \
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