pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building on local'
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing on local'
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying to Docker'
                echo 'docker compose up'
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build failed. Check the logs for more details.'
        }
    }
}