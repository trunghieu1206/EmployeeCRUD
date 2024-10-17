pipeline {
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                
            }
        }
        stage('Build') {
            steps {
                sh "mvn clean"
                echo "cleaning previous docker compose"
                sh "docker compose down"
                echo "building using docker compose"
                sh "docker compose build"
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying using docker compose'
                sh '''
                docker compose up -d
                '''
            }
        }
        stage('Cleanup'){
            steps{
                echo "cleaning up"
                sh "docker compose down "
            }
        }
    }
    post{
        success{
            echo "pipeline completed successfully"
        }
        failure{
            echo "pipeline failed"
        }
    }
}