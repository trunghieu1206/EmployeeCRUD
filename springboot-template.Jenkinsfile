pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                // Explicitly cloning from a Git repository
                git url: 'https://github.com/trunghieu1206/EmployeeCRUD', branch: 'master'

                sh '''
                    ls -la
                    chmod +x mvnw
                '''
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
                        -Dsonar.projectKey=org.sonarqube:scmgalaxy1 \
                        -Dsonar.projectName=app \
                        -Dsonar.projectVersion=1.0 \
                        -Dsonar.sources=src \
                        -Dsonar.login=admin \
                        -Dsonar.password=Hieuhieuhieu1! \
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