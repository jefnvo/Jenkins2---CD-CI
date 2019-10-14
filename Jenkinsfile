pipeline {

    agent any
    tools {
    	maven 'Maven 3.5.4'
    	jdk 'java8'
    }
    parameters {
		  choice choices: ['dev', 'ci', 'hom', 'hom03', 'prd', 'tst', 'tit'], description: 'A test', name: 'environment'
	}

    stages {
        stage('BuildDefault') {
            steps {
                script {
                	echo "Hello, we're verify if it's necessary run webpack"
			sh 'curl --version'
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
					if(runWebpack){
						echo "running webpack"
				        sh 'mvn -f ./my-app -T 4 install -nsu -P${params.environment}'
					}else{
						sh 'mvn -f ./my-app -T 4 install -nsu -Dmaven.test.skip=true -Dnpm.skip=true -P${params.environment}'
					}
                }
               
                
            }
        }
    }
}
def build(runWebpack){

}
def verify(){
	
	return runWebpack
}
