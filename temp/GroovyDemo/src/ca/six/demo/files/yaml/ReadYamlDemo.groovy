package ca.six.demo.files.yaml

import groovy.yaml.YamlSlurper

File file = new File("PR_Check.yml")
def configs = new YamlSlurper().parse(file)

// 1. config是一个LazyMap类型. 即[k:v]的字面量写法.
println configs.getClass()
// println configs

println configs.name //=> Android CI

configs.jobs.each {
    println "============================"
    println it.getClass() //=> LinkedHashMap$Entry类型. 即lint = {runs=ubuntu, ..}
    println it.key
    println it.value."runs-on"
    println it.value.steps.getClass() // it.values.steps是一个ArrayList.
    println it.value.steps[0] //=> [uses:actions/checkout@v1]
    println it.value.steps[1] //=> [uses:actions/checkout@v1]
}




