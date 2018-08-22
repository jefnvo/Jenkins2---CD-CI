pipeline {
    agent any
    tools {
    	maven 'Maven 3.5.4'
    	jdk 'jdk8'
    }
    stages {
        stage('Build') {
            steps {
                script {
                	echo "Hello, we're verify if it's necessary run webpack"
                	runWebpack = "true"

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