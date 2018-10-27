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
      sh './gradlew assembleDebug'
    }}
  }
  post {
    success {
      archiveArtifacts 'app/build/outputs/apk/*/*.apk'
    }
  }
}