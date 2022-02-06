pipeline {
	agent any
	tools {
		maven 'M3'
	}
	stages {
		stage('Checkout'){
			steps {
 				git 'https://github.com/Nifori/NifoBot'
                sh 'git fetch'
				sh 'git pull'
			}
		}
		stage('Check') {
			steps {			
				echo 'Check..'
				sh 'echo $JAVA_HOME'
				sh 'java -version'
				sh 'mvn -v'
			}
		}
		stage('Test') {
			steps {			
				echo 'Testing..'
				sh 'mvn test'
				junit skipPublishingChecks: true, testResults:'**/target/surefire-reports/*.xml'
			}
		}
		stage('Build') {
			steps {
				echo 'Building..'
				sh 'mvn package -DskipTests'
			}
		}

	}
}
