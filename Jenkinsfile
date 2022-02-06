pipeline {
	agent any
	tools {
		maven 'M3'
		dockerTool 'D3'
	}
	    environment {
            // Using returnStatus
            CV = """${sh(
                    returnStatus: true,
                    script: '$(head -n 1 CHANGELOG)'
                )}"""
        }
	stages {
// 		stage('Test') {
// 			steps {
// 				sh 'mvn test'
// 				junit skipPublishingChecks: true, testResults:'**/target/surefire-reports/*.xml'
// 			}
// 		}
		stage('Build') {
			steps {
				echo 'Building..'
				sh 'mvn package -DskipTests'
				sh 'printenv'
				    sh 'echo $CV'
				    sh 'echo ${CV}'
				    sh 'echo ${env.CV}'
				sh 'docker build -t nifobot-service:${env.VERSION} --build-arg JAR_FILE=service/build/nifobot-service.jar .'
			}
		}

	}
}
