pipeline {
	agent any
	tools {
		maven 'M3'
	}
	stages {
// 		stage('Checkout'){
// 			steps {
//  				git url: 'https://github.com/Nifori/NifoBot', branch: GIT_BRANCH
// 			}
// 		}
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
			}
		}

	}
}
