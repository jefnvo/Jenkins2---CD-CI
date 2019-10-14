pipeline {
    agent { label 'docker' }
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
