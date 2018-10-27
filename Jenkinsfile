pipeline {
  agent {
    dockerfile {
      filename 'Dockerfile'
      dir 'docker'
      label 'docker'
    }
  }
  options {
    ansiColor('xterm')
    timestamps()
  }
  stages {
    stage('Build') {
    steps {
      withCredentials([file(credentialsId: '7a758683-7636-4a0c-a3c6-8b72f17897c8', variable: 'GRADLE')]) {
      withCredentials([file(credentialsId: '3026e421-b72e-4484-9468-bce33ee01ae9', variable: 'KEYSTORE')]) {
        sh '''cp $GRADLE ./release.gradle
        cp $KEYSTORE ./release.keystore
        ./gradlew assembleRelease'''
      }
      }
    }}
  }
  post {
    success {
      archiveArtifacts 'app/build/outputs/apk/*/*.apk'
    }
  }
}