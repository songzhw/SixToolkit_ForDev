
import groovy.json.JsonSlurper

/**
 * Created by songzhw on 2016-12-07.
 */


coreName = "miao"

lineSeparator = System.getProperty("line.separator");
def reader = new FileReader('test2.json')
ajson = new JsonSlurper().parse(reader)

println '======================================='
createSubFolder()
write2ItemFile(coreName, ajson)
println '======================================='

// ========================================
// ========     private methods    ========
// ========================================
def createSubFolder(){
    def itemFolder = new File("./item")
    if(!itemFolder.exists()) {
        itemFolder.mkdir()
    }
}

// generate Object or List item class file
def write2ItemFile(ikey, ivalue) {
    def sb2 = new StringBuilder()
    add(sb2, "public class ${ikey.capitalize()} {")
    ivalue.each { jsonKey, jsonValue ->
        String type = getType(jsonKey, jsonValue)
        add1(sb2, "@SerializedName(\"$jsonKey\")")
        add1(sb2, "private $type $jsonKey;")

        if(type == "String" || type == "boolean") {

        } else if(type.startsWith("List")) {
            def subtype = ""
            def pattern = ~/List<(.*)>/
            type.find(pattern){
                subtype = it[1]
            }
            write2ItemFile(subtype, jsonValue[0])

        } else { // object type
            write2ItemFile(jsonKey, jsonValue)
        }
    }
    addLine(sb2)

    ivalue.each{jsonKey, jsonValue ->
        String type = getType(jsonKey, jsonValue)
        add1(sb2, "public $type get${jsonKey.capitalize()}() {")
        add2(sb2, "return $jsonKey;")
        add1(sb2, "}")

        add1(sb2, "public void set${jsonKey.capitalize()}($type $jsonKey) {")
        add2(sb2, "this.$jsonKey = $jsonKey;")
        add1(sb2, "}")
    }
    add(sb2, "}")

//    add(sb2, " * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *")
    write2File("./item/${ikey.capitalize()}.java", sb2.toString())

//    println sb2
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

def add(sb3, content){
    sb3 << "$content" << lineSeparator
}

def add2(sb3, content) {
    sb3 << "\t\t$content" << lineSeparator
}

def add1(sb3, content) {
    sb3 << "\t$content" << lineSeparator
}

def addLine(sb3){
    sb3 << lineSeparator
}