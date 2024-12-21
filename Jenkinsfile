pipeline {
    agent any

    tools {
        maven 'Maven_3.9.9' 
    }

    environment {
        DOCKER_IMAGE = "spring-boot-app3"
    }

    stages {
        stage('Preparation') {
            steps {
                echo "Setting up Git configurations..."
                sh 'git config --global http.postBuffer 524288000'
                sh 'git config --global http.version HTTP/1.1'
                sh 'git config --global fetch.timeout 600'
            }
        }

        stage('Checkout') {
            steps {
                retry(3) {
                    echo "Cloning repository..."
                    git branch: 'main', url: 'https://github.com/kholoud001/PigeonSkyRaceSecurity-deploiement.git'
                }
            }
        }

        stage('Build') {
            steps {
                echo "Building project with Maven..."
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                echo "Building Docker image..."
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Push') {
            steps {
                echo "Pushing Docker image to Docker Hub..."
                sh "docker push ${DOCKER_IMAGE}"
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying Docker container..."
                sh 'docker stop pigeon-spring-security || true'
                sh 'docker rm pigeon-spring-security || true'
                sh "docker run -d --name pigeon-spring-security -p 8085:8080 ${DOCKER_IMAGE}"
            }
        }
    }

    post {
        always {
            echo "Cleaning up workspace..."
            cleanWs()
        }

        success {
            echo "Build and deployment successful."
        }

        failure {
            echo "Build or deployment failed."
        }

        always {
            echo "Publishing JUnit test results..."
            junit 'target/surefire-reports/*.xml'
        }
    }
}
