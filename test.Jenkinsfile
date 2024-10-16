pipeline {
    agent {
        docker {
            image 'trunghieu1206/maven-agent:15-10-2024'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    environment {
        // points to the Docker daemon's address, which is being
        // forwarded by the socat container
        DOCKER_HOST = 'tcp://c3cb02c61d96:2375'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker-compose -f docker-compose.yml build'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker-compose up -d'
            }
        }
        stage('Cleanup') {
            steps {
                sh 'docker image prune -f'
            }
        }
    }
}