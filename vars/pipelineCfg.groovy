def call() {
  Map pipelineCfg = readYaml(file: "${WORKSPACE}/resources/pipeline.yaml")
  return pipelineCfg
}