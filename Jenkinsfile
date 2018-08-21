pipeline {
    agent any
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
                    if(runWebpack.equals("false")){
                    	echo "skipping webpack"
                    	//sh 'mvn -f ./source -T 4 install -nsu -Dmaven.test.skip=true -Dnpm.skip=true'
                    } else {
                    	echo "running webpack"
                    	//sh 'mvn -f ./source -T 4 install -nsu -Pdev'
                    }

                }
               
                
            }
        }
    }
}