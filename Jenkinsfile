pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "spring-boot-app3"
        DOCKER_CONTAINER = "spring-boot-app3-container"
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/kholoud001/PigeonSkyRaceSecurity-deploiement.git'
            }
        }
        stage('Build and Compile') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Verify Docker') {
            steps {
                sh 'docker --version'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t spring-boot-app3 .'
            }
        }
        stage('Deploy in Docker') {
            steps {
                sh '''
                docker stop spring-boot-app3-container || true
                docker rm spring-boot-app3-container || true
                docker run -d --name spring-boot-app3-container -p 8080:8080 spring-boot-app3
                '''
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
