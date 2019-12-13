def call(Map stageParams) {
    dir(stageParams.foldername) {
        sh script: stageParams.script
    }
}

