pipeline {
	agent any
	tools {
		maven 'M3'
		dockerTool 'D3'
	}
	    environment {
            // Using returnStatus
            VERSION = """${sh(
                    returnStatus: true,
                    script: '$(head -n 1 CHANGELOG)'
                )}"""
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


				try {
				    sh 'echo $VERSION'
				} catch (Exception e){
				    echo e.toString()
				}
				try {
				    sh 'echo ${VERSION}'
				} catch (Exception e){
				    echo e.toString()
				}
				try {
				    sh 'echo ${env.VERSION}'
				} catch (Exception e){
				    echo e.toString()
				}
				sh 'docker build -t nifobot-service:${env.VERSION} --build-arg JAR_FILE=service/build/nifobot-service.jar .'
			}
		}

	}
}
