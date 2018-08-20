abcs = ['a', 'b', 'c']

pipeline {
	agent any
	stages {
		stage('Build') {
			steps {
				iterateCommits()
			}
		}
	}
}


@NonCPS
def iterateCommits(){	
	COMMITS = sh (
		script: "git diff --name-only $GIT_PREVIOUS_COMMIT $GIT_COMMIT", 
		returnStdout: true
		).trim()
	echo "eeeeeeeeeeee ooooo ${COMMITS}"

	COMMITS.each {item -> 
		if ( ${item}.find {it == ".js"} ) {
			sh "echo entrei"
		}
	}
}

@NonCPS // has to be NonCPS or the build breaks on the call to .each
def echo_all(list) {
    list.each { item ->
        echo "Hello ${item}"
    }
}
// outputs all items as expected

@NonCPS
def loop_of_sh(list) {
    list.each { item ->
        sh "echo Hello ${item}"
    }
}
// outputs only the first item

@NonCPS
def loop_with_preceding_sh(list) {
    sh "echo Going to echo a list"
    list.each { item ->
        sh "echo Hello ${item}"
    }
}
// outputs only the "Going to echo a list" bit

//No NonCPS required
def traditional_int_for_loop(list) {
    sh "echo Going to echo a list"
    for (int i = 0; i < list.size(); i++) {
        sh "echo Hello ${list[i]}"
    }
}
// echoes everything as expected