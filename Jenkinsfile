pipeline {
    agent any
    environment {
        JAVA_HOME = 'D:\\JDK'
        PATH = "D:\\JDK\\bin;C:\\Windows\\System32;C:\\Windows;${env.PATH}"
    }
    stages {
        stage('Build') {
            steps {
                bat 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                bat 'mvn package -DskipTests'
            }
        }
    }
}