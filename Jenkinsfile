pipeline {
    agent any
    
    tools {
        // Use the Maven tool configured in Jenkins Global Tool Configuration
        maven 'Maven-3.9.12'
    }
    
    environment {
        // CHANGE THIS to your Docker Hub username
        DOCKER_IMAGE = "joel5040/my-java-app"
        GIT_REPO = "https://github.com/joel5040/my-java-app.git"
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
        
        // Stage 3: Increment Patch Version
        stage('Increment Patch Version') {
            steps {
                script {
                    sh '''
                        mvn build-helper:parse-version versions:set \
                          -DnewVersion=${parsedVersion.majorVersion}.${parsedVersion.minorVersion}.${parsedVersion.nextIncrementVersion} \
                          versions:commit
                    '''
                }
            }
        }
        
        // Stage 4: Version Bump and Push to Jenkins Jobs Branch
        stage('Version Bump & Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'github-creds',
                    usernameVariable: 'GIT_USER',
                    passwordVariable: 'GIT_TOKEN'
                )]) {
                    sh '''
                        git config user.email "jenkins@example.com"
                        git config user.name "Jenkins CI"
                        
                        echo "=== Git Status ==="
                        git status
                        echo "=== Git Branch ==="
                        git branch -a
                        echo "=== Git Config ==="
                        git config --list
                        
                        git remote set-url origin "https://${GIT_USER}:${GIT_TOKEN}@github.com/joel5040/my-java-app.git"
                        
                        git add .
                        git commit -m "ci: version bump" || echo "No changes to commit"
                        git push origin HEAD:jenkins-jobs
                    '''
                }
            }
        }
        
        // Stage 5: Create Docker image
        stage('Build Docker') {
            steps {
                sh """
                    docker build -t ${DOCKER_IMAGE}:latest .
                    docker tag ${DOCKER_IMAGE}:latest ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
                """
            }
        }
        
        // Stage 6: Push to Docker Hub
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
