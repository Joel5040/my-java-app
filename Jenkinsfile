pipeline {
    agent any
    
    environment {
        // CHANGE THIS to your Docker Hub username
        DOCKER_IMAGE = "joel5040/my-java-app"
    }
    
    stages {
        // Stage 1: Get code from GitHub
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        // Stage 2: Build and test Java app
        stage('Build & Test') {
            steps {
                sh 'mvn clean test package'
            }
        }
        
        // Stage 3: Create Docker image
        stage('Build Docker') {
            steps {
                sh """
                    docker build -t ${DOCKER_IMAGE}:latest .
                    docker tag ${DOCKER_IMAGE}:latest ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                """
            }
        }
        
        // Stage 4: Push to Docker Hub
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                        echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
                        docker push ${DOCKER_IMAGE}:latest
                        docker push ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                    """
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline finished'
        }
        success {
            echo '✅ Success!'
        }
        failure {
            echo '❌ Failed!'
        }
    }
}
