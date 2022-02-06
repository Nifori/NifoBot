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
				sh 'mvn package -DskipTests'
			}
		}
		stage('Docker'){
			when {
			    branch 'master'
			}
			steps {
        	    sh 'docker build -t nifobot-service:latest --build-arg JAR_FILE=service/build/nifobot-service.jar .'
                sh 'docker build -t nifobot-datamodel:latest --build-arg JAR_FILE=datamodel/build/nifobot-datamodel.jar .'
			}
		}
	}
}
