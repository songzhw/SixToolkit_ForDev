package ca.six.demo.files.json

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

File jsonFile = new File("../../../../../../res/package.json")
def pkg = new JsonSlurper().parse(jsonFile)

pkg.name = "ReactNative101"

//在内存中打印出来结果
String prettyJson = JsonOutput.prettyPrint(JsonOutput.toJson(pkg))
println prettyJson

// 写入文件
jsonFile.write(prettyJson)
