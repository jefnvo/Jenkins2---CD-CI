// abcs = ['a', 'b', 'c']

// pipeline {
// 	agent any
// 	stages {
// 		stage('Build') {
// 			steps {
// 				iterateCommits()
// 			}
// 		}
// 	}
// }



// def iterateCommits(){	
// 	COMMITS = sh (
// 		script: "git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT", 
// 		returnStdout: true
// 		).trim()
// 	// echo "eeeeeeeeeeee ooooo ${COMMITS}"
// 	script {
// 		if ( ${COMMITS}.find {it == ".js"} ) {
// 			sh "echo entrei"
// 		} 
// 	}
// }

// @NonCPS // has to be NonCPS or the build breaks on the call to .each
// def echo_all(list) {
//     list.each { item ->
//         echo "Hello ${item}"
//     }
// }
// // outputs all items as expected

// @NonCPS
// def loop_of_sh(list) {
//     list.each { item ->
//         sh "echo Hello ${item}"
//     }
// }
// // outputs only the first item

// @NonCPS
// def loop_with_preceding_sh(list) {
//     sh "echo Going to echo a list"
//     list.each { item ->
//         sh "echo Hello ${item}"
//     }
// }
// // outputs only the "Going to echo a list" bit

// //No NonCPS required
// def traditional_int_for_loop(list) {
//     sh "echo Going to echo a list"
//     for (int i = 0; i < list.size(); i++) {
//         sh "echo Hello ${list[i]}"
//     }
// }
// // echoes everything as expected

pipeline {
    agent any

    stages {
        stage('Example') {
            steps {
                echo 'Hello World'
                

                script {
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
                    if(runWebpack.equals("false")){
                    	echo "skipping webpack"
                    	sh 'mvn -T 4 install -nsu -Dmaven.test.skip=true -Dnpm.skip=true'
                    } else {
                    	sh 'mvn -T 4 install -nsu -Pdev'
                    }

                }
               
                
            }
        }
    }
}