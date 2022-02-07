package ca.six.demo.files.yaml

import groovy.yaml.YamlSlurper

File file = new File("PR_Check.yml")
def configs = new YamlSlurper().parse(file)

configs.each {
    println it
}