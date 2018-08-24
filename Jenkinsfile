pipeline {
	checkout([$class: 'GitSCM', branches: [[name: '*/master']], 
											userRemoteConfigs: [[credentialsId: 'e1dd82b6-9941-4e95-8310-75fecefe6aa5', 
											url: 'https://github.com/jefnvo/Jenkins2---CD-CI']]])

    parameters {
  		choice choices: 	['mvn clean install -nsu', 
  							'mvn -f ./my-app -T 4 install -nsu -Dmaven.test.skip=true -Dnpm.skip=true', 
  							'mvn -f ./my-app -T 4 install -nsu -Pci'], 
  							description: 'Build parameters', name: 'Build Type'
	}

    agent any
    tools {
    	maven 'Maven 3.5.4'
    	jdk 'java8'
    }
    stages {
        stage('Build') {
            steps {
                script {
                	echo "Hello, we're verify if it's necessary run webpack"
                	runWebpack = verify()                    
                   	build(runWebpack)
                }
               
                
            }
        }
    }
}
def build(runWebpack){
	if(runWebpack){
		echo "skipping webpack"
        sh 'mvn -f ./my-app -T 4 install -nsu -Dmaven.test.skip=true -Dnpm.skip=true'
	}else{
		echo "running webpack"
        sh 'mvn -f ./my-app -T 4 install -nsu -Pdev'
	}
}
def verify(){
	runWebpack = "false"
	COMMMITS = sh (
					script: "git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT", 
					returnStdout: true
					)
	COMMMITS = COMMMITS.split("\n")

	for(i=0; i<COMMMITS.size();i++){
		if(COMMMITS[i].endsWith(".js") ){
			echo "that is a javaScript archive and should running webpack"
			runWebpack = "true"
			break;
	   	}
	}
	return runWebpack
}