
pipeline {

    agent any
    parameters {
		  choice choices: ['dev', 'ci', 'hom', 'hom03', 'prd', 'tst', 'tit'], description: 'A test', name: 'environment'
	}

    stages {
        stage('BuildDefault') {
            steps {
                script {
                	echo "Hello, we're verify if it's necessary run webpack"
			        sh 'curl --version'
                }
               
                
            }
        }
    }
}

