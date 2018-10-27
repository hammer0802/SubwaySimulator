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
      sh './gradlew assembleRelease'
    }}
  }
  post {
    success {
      archiveArtifacts 'app/build/outputs/apk/*/*.apk'
    }
  }
}