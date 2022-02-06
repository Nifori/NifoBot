pipeline {
	agent any
	tools {
		maven 'M3'
		docker 'D3'
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
				sh 'docker -v'
			}
		}

	}
}
