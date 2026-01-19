pipeline {
    agent any

    tools {
        maven 'maven-3.9'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saikarthikdodda/backend.git'
            }
        }

        stage('Build with Maven') {
    steps {
        sh 'chmod +x mvnw'
        sh './mvnw clean package -DskipTests'
    }
}


        stage('Build Docker Image') {
            steps {
                sh 'docker build -t backend-app .'
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                docker rm -f backend || true
                docker run -d --name backend -p 8081:8081 backend-app
                '''
            }
        }
    }
}
