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
        String type = getType(jsonKey, jsonValue)
        println("private $type $jsonKey;")
    }
}


def write2File(fileFullName, content){
    def file = new File(fileFullName)
    file.withWriter{ Writer writer ->
        writer.append(content)
    }
}

def getType(key, value){
    def valueType = value.getClass()
    switch(valueType){
        case 'class java.lang.String':
        case 'class java.lang.Integer':
        case 'class java.math.BigDecimal':
            return 'String';
        case 'class java.lang.Boolean':
            return 'boolean';
        case 'class java.util.ArrayList':
            def listSubType = getType("${key}Item", value[0])
            return "List<$listSubType>"
        case 'class groovy.json.internal.LazyMap':
            def objectType = key.capitalize();
            return objectType;
    }
}
