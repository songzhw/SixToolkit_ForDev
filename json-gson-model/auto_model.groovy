package tool.auto_model

import groovy.json.JsonSlurper

/**
 * Created by songzhw on 2016-12-07.
 */
coreName = "miao"

lineSeparator = System.getProperty("line.separator");
def reader = new FileReader('test2.json')
ajson = new JsonSlurper().parse(reader)

println '======================================='
sb = new StringBuilder();
//todo  create a item directory if not existing
json2Model()
println '======================================='

// ========================================
// ========     private methods    ========
// ========================================

def json2Model() {
    add("public class ${coreName.capitalize()} {")
    ajson.each { jsonKey, jsonValue ->
        String type = getType(jsonKey, jsonValue)
        add1("@SerializedName(\"$jsonKey\")")
        add1("private $type $jsonKey;")
    }
    addLine()

    ajson.each{jsonKey, jsonValue ->
        String type = getType(jsonKey, jsonValue)
        add1("public $type get${jsonKey.capitalize()}() {")
        add2("return $jsonKey;")
        add1("}")

        add1("public void set${jsonKey.capitalize()}($type $jsonKey) {")
        add2("this.$jsonKey = $jsonKey;")
        add1("}")
    }
    add("}")
    print sb
}

def add(content) {
    sb << "$content" << lineSeparator
}

def add2(content) {
    sb << "\t\t$content" << lineSeparator
}

def add1(content) {
    sb << "\t$content" << lineSeparator
}

def addLine(){
    sb << lineSeparator
}


def write2File(fileFullName, content) {
    def file = new File(fileFullName)
    file.withWriter { Writer writer ->
        writer.append(content)
    }
}

def getType(key, value) {
    def valueType = value.getClass()
    switch (valueType) {
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
