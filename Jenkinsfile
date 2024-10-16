pipeline {
    agent {
        node {
            label 'docker-agent-maven'
        }
    }
    stages {
        stage('Compile') {
            steps {
                sh '''
                ./mvnw compile
                '''
            }
        }
        stage('Test') {
            steps {
                sh '''
                ./mvnw test
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