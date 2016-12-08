package tool.auto_model

import groovy.json.JsonSlurper

/**
 * Created by songzhw on 2016-12-07.
 */
def reader = new FileReader('test2.json')
ajson = new JsonSlurper().parse(reader)

ajson.each { key, value ->
    println "$key (type = ${value.getClass()})"
}


def getType(key, value){
    
}
