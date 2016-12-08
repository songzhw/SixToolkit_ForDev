package tool.auto_model

import groovy.json.JsonSlurper

/**
 * Created by songzhw on 2016-12-07.
 */
def reader = new FileReader('test2.json')
ajson = new JsonSlurper().parse(reader)

ajson.each { key, value ->
    println "$key (type = ${value.getClass()})"
    println "TYPE : ${getType(key, value)}"
    println "-----------------------------------------------------------------"
}


def getType(key, value){
    valueType = value.getClass()
    switch(valueType){
        case 'class java.lang.String':
            return 'String';
        case 'class java.lang.Boolean':
            return 'boolean';
        case 'class java.lang.Integer':
            return 'int';
        case 'class java.util.ArrayList':
            listSubClass = value[0].getClass()
            //println "getType() : ArrayList : listSubClass = $listSubClass"
            listSubType = getTypeFromWholePath("${key}Item", value[0])
            return "ArrayList<$listSubType>"
        case 'class groovy.json.internal.LazyMap':
            //println "$key -- ${key.getClass()}"
            objectType = key.capitalize();
            return objectType;
    //return 'Object'
    }
}
