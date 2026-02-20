pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.12'
        jdk 'JDK17'
    }
    
    environment {
        DOCKER_IMAGE = "joel5040/my-java-app"
    }
    
    stages {
        // Stage 1: Get code from GitHub
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        // Stage 2: Build Java app (runs on all branches now)
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        
        // Stage 3: Run Tests
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        // Stage 4: Create Docker image
        stage('Build Docker') {
            steps {
                sh """
                    docker build -t ${DOCKER_IMAGE}:latest .
                    docker tag ${DOCKER_IMAGE}:latest ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                """
            }
        }
        
        // Stage 5: Push to Docker Hub
        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh """
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push ${DOCKER_IMAGE}:latest
                        docker push ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                    """
                }
            }
        }
    }
    
    post {
        always { echo 'Pipeline finished' }
        success { echo '✅ Success!' }
        failure { echo '❌ Failed!' }
    }
}
