pipeline {
	agent any
	tools {
		maven 'M3'
		dockerTool 'D3'
	}
	stages {
	    stage('Get Changelog') {
	        steps {
	            sh 'export version=$(head -n 1 CHANGELOG)'
	        }
	    }
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
				sh 'docker build -t nifobot-service:$version --build-arg JAR_FILE=service/build/nifobot-service.jar .'
			}
		}

	}
}
