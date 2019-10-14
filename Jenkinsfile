pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Deploy') {
            steps {
                echo 'Deploy Jenkins'
                sh 'curl -v'
            }
        }
    }
}
