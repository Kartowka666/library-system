pipeline {
    agent any
    environment {
        JAVA_HOME = 'D:\\JDK'
        PATH = "D:\\JDK\\bin;C:\\Windows\\System32;C:\\Windows;${env.PATH}"
    }
     stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build') {
            steps { bat 'mvn clean compile' }
        }
        stage('Static Analysis') {        
            steps { bat 'mvn checkstyle:check' }
        }
        stage('Test') {
            steps { bat 'mvn test' }
        }
        stage('Package') {
            steps { bat 'mvn package' }
        }
    }
}