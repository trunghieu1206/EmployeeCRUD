pipeline {
    agent {
        node {
            label 'docker-agent-maven'
        }
    }
    stages {
        stage('Build') {
            steps {
                echo "Building.."
                sh '''
                ./mvnw clean
                '''
            }
        }
        stage('Test') {
            steps {
                echo "Testing.."
                sh '''
                ./mvnw spring-boot:run
                '''
            }
        }
        stage('Deliver') {
            steps {
                echo 'Deliver....'
                sh '''
                echo "doing delivery stuff.."
                '''
            }
        }
    }
}