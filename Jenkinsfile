pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "spring-boot-app3"
        DOCKER_CONTAINER = "spring-boot-app3-container"
    }
    options {
        durableTaskOption(org.jenkinsci.plugins.durabletask.BourneShellScript.HEARTBEAT_CHECK_INTERVAL: '86400')  // Heartbeat check interval
    }
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/kholoud001/PigeonSkyRaceSecurity-deploiement.git'
            }
        }
        stage('Clean Workspace') {
            steps {
                cleanWs()  // Clean the workspace before building
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
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }
        stage('Deploy in Docker') {
            steps {
                script {
                    sh '''
                    docker stop $DOCKER_CONTAINER || true
                    docker rm $DOCKER_CONTAINER || true
                    docker run -d --name $DOCKER_CONTAINER -p 8080:8080 $DOCKER_IMAGE
                    '''
                }
            }
        }
    }
    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
