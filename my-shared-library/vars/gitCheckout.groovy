def call(Map stageParams) {

    checkout([
        $class: 'GitSCM',
        branches: [[name:  stageParams.branch ]],
        userRemoteConfigs: [[credentialsId: stageParams.credid ]],
        userRemoteConfigs: [[ url: stageParams.url ]]
    ])
  }
