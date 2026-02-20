pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.12'
        jdk 'JDK17'
    }
    
    environment {
        DOCKER_IMAGE = "joel5040/my-java-app"
        GIT_REPO = "https://github.com/joel5040/my-java-app.git"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build & Test') {
            steps {
                sh 'mvn clean test package'
            }
        }
        
        // keep your other stages unchanged...
    }
    
    post {
        always { echo 'Pipeline finished' }
        success { echo '✅ Success!' }
        failure { echo '❌ Failed!' }
    }
}
