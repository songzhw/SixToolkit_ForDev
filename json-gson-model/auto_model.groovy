package tool.auto_model

import groovy.json.JsonSlurper

/**
 * Created by songzhw on 2016-12-07.
 */
def reader = new FileReader('test2.json')
ajson = new JsonSlurper().parse(reader)

println '======================================='
sb = new StringBuilder()
json2Model()
println '======================================='


// ========================================
// ========     private methods    ========
// ========================================

def json2Model(){
    ajson.each {jsonKey, jsonValue ->
        getType(jsonKey, jsonValue)
    }
}


def write2File(fileFullName, content){
    def file = new File(fileFullName)
    file.withWriter{ Writer writer ->
        writer.append(content)
    }
}

def getType(key, value){
    valueType = value.getClass()
    println "szw $valueType"
    switch(valueType){
        case 'class java.lang.String':
            return 'String';
        case 'class java.lang.Boolean':
            return 'boolean';
        case 'class java.lang.Integer':
            return 'int';
        case 'class java.util.ArrayList':
            listSubClass = value[0].getClass()
            listSubType = getType("${key}Item", value[0])
            return "List<$listSubType>"
        case 'class groovy.json.internal.LazyMap':
            objectType = key.capitalize();
            return objectType;
    }
}
