pipeline {
	agent any
	tools {
		maven 'M3'
		dockerTool 'D3'
	}
	stages {
		stage('Test') {
			steps {			
				sh 'mvn test'
				junit skipPublishingChecks: true, testResults:'**/target/surefire-reports/*.xml'
			}
		}
		stage('Build') {
			steps {
				echo 'Building..'
				sh 'mvn package -DskipTests'
				sh 'export version=$(head -n 1 CHANGELOG)'
				sh 'docker build -t nifobot-service:$version --build-arg JAR_FILE=service/build/nifobot-service.jar .'
			}
		}

	}
}
