pipeline {
	parameters {
		choice choices: ['mvn -f ./my-app -T 4 install -nsu -Dmaven.test.skip=true -Dnpm.skip=true', 
						 'mvn -f ./my-app -T 4 install -nsu -Pci'], 
						 description: 'Build parameters', name: 'BuildType'
	}

    agent any
    tools {
    	maven 'Maven 3.5.4'
    	jdk 'java8'
    }
    stages {
        stage('defaultBuild') {
            steps {
                script {
                	echo "Hello the default parameter is ${params.BuildType[1]}"
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
		echo "running webpack"
        sh 'mvn -f ./my-app -T 4 install -nsu -Pdev'
	}else{
		echo "skipping webpack"
        sh 'mvn -f ./my-app -T 4 install -nsu -Dmaven.test.skip=true -Dnpm.skip=true'
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