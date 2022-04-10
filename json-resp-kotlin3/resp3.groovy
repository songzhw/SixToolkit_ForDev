package ca.six.grovwork.autojson3

import groovy.json.JsonSlurper
import org.apache.groovy.json.internal.LazyMap

// 通过 `this.args[i]`, 得以得到外部参数
basicFileName = this.args[0] ?: ""
packageName = this.args[1] ?: ""

// 创建生成的目录, 就在本目录的output子目录里
path = "./output/" //要以 "/" 结尾
def directory = new File(path)
directory.mkdirs()

responseName = "${basicFileName.capitalize()}Data"
responseFileName = "${path}${responseName}.kt"
lineSeparator = System.getProperty("line.separator");

variableNum = 0 //声明为int variableNum = 0, 就会是局部变量了, 在parseJson()等方法里没法使用了

// src.json 与 script file要在同一目录里
def reader = new FileReader('src.json')
ajson = new JsonSlurper().parse(reader)

println '======================================='
def content = parseJson(ajson, responseName)
output(responseFileName, content)
println '======================================='


def parseJson(jsons, className) {
    def sb = new StringBuilder()
    sb << "package ${packageName}" << lineSeparator
    sb << lineSeparator //这一块不用太在意, 粘贴时AndroidStudio会帮你修正package的

    sb << "import java.util.ArrayList;" << lineSeparator
    sb << "import org.json.JSONArray;" << lineSeparator
    sb << "import org.json.JSONObject;" << lineSeparator
    sb << lineSeparator

    sb << "data class ${className}(" << lineSeparator
    generateFields(jsons, sb)
    sb << ")" << lineSeparator

    return sb
}

def generateFields(jsons, sb) {
    jsons.each { key, value ->
        def type = getType(key, value)
        sb << "\tvar $key: $type," << lineSeparator

        // generate child class files
        if(value.getClass() == LazyMap){
            writeSubFiles(key, value)
        }

        if(value.getClass() == ArrayList){
            def childType = value[0].getClass()
            if(childType == LazyMap){
                writeSubFiles("${key.capitalize()}Item", value[0])
            }
        }
    }
}

def output(fileFullName, content) {
    def file = new File(fileFullName)
    file.withWriter { Writer writer ->
        writer.append(content)
    }
}

//key参数是防备有JsonArray<自定义类>的， 这样可以给子Item命名
def getType(key, value) {
    def valueType = value.getClass()
    switch (valueType) {
        case String:
            return 'String';
        case Boolean:
            return 'Boolean';
        case Integer:
            return 'Int';
        case ArrayList:
            listSubClass = value[0].getClass()
            listSubType = listSubClass == LazyMap.class ?
                    "${key.capitalize()}Item" :
                    getType("${key}Item", value[0]);
            return "ArrayList<$listSubType>"
        case LazyMap:
            objectType = key.capitalize();
            return objectType;
    }
}

def writeSubFiles(fkey, fvalue) {
    def sb2 = parseJson(fvalue, fkey.capitalize())
    output("${path}${fkey.capitalize()}.kt", sb2)
}