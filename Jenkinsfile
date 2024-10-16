CODE_CHANGES = getGitChanges()
pipeline {
    agent {
        node {
            label 'docker-agent-maven'
        }
    }
    stages {
        stage('Checkout'){
            steps{
                git url: 'https://github.com/trunghieu1206/EmployeeCRUD.git', branch: 'main'
            }
        }
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