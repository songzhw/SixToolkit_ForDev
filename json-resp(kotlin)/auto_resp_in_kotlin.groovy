import groovy.json.JsonSlurper

//TODO put this as a command argument
//TODO {packageName} also should be assigned by command argument
basicFileName = 'Home'

path = "./output/" //要以 "/" 结尾
responseName = "${basicFileName.capitalize()}Response"
responseFileName = "${path}${responseName}.kt"
lineSeparator = System.getProperty("line.separator");

def directory = new File(path)
directory.mkdirs()
def reader = new FileReader('src.json')
ajson = new JsonSlurper().parse(reader)

variableNum = 0 //声明为int variableNum = 0, 就会是局部变量了, 在parseJson()等方法里没法使用了

println '======================================='
def content = parseJson(ajson, responseName)
output(responseFileName, content)
println '======================================='


def parseJson(jsons, className) {
    def sb = new StringBuilder()
    sb << "package your.company.data.entity" << lineSeparator
    sb << lineSeparator  //这一块不用太在意, 粘贴时AndroidStudio会帮你修正package的

    sb << "import java.util.ArrayList;" << lineSeparator
    sb << "import org.json.JSONArray;" << lineSeparator
    sb << "import org.json.JSONObject;" << lineSeparator
    sb << lineSeparator

    sb << "class ${className} (json : JSONObject) {" << lineSeparator
    jsons.each { key, value ->
        def type = getType(key, value)
        sb << "\tvar $key : $type " << lineSeparator  //赋值在init()中, 所以不用加lateinit. 也自然没了primitive type的问题
    }
    sb << lineSeparator

    sb << "\tconstructor(jsonStr : String) : this(JSONObject(jsonStr)) {}"
    sb << lineSeparator
    sb << lineSeparator

    sb << "\tinit {" << lineSeparator
    jsons.each { key, value ->
        def type = getType(key, value)
        println "parseJson() key = $key, type = $type, value = $value "

        if (type.startsWith("ArrayList")) {
            def subtype = ""
            def pattern = ~/ArrayList<(.*)>/
            type.find(pattern) {
                subtype = it[1]
            }
            sb << lineSeparator

            def ary = "array${variableNum}"
            sb << "\t\tval ${ary} = json.optJSONArray(\"$key\")" << lineSeparator

            if (subtype.equals("Long") || subtype.equals("Int")
                    || subtype.equals("String") || subtype.equals("Boolean")) {
                sb << "\t\t$key = ArrayList()" << lineSeparator
                sb << "\t\tfor(i in 0 until ${ary}.length()){" << lineSeparator
                sb << "\t\t\tval asub = ${ary}.opt$subtype(i) " << lineSeparator
                sb << "\t\t\t${key}.add(asub)" << lineSeparator
                sb << "\t\t}" << lineSeparator
                sb << lineSeparator
            } else {
                writeSubFiles(subtype, value[0])

                sb << "\t\t${key} = create${subtype}(${ary})" << lineSeparator
                sb << lineSeparator

            }
            variableNum++

        } //end of ArrayList<subType>
        else if (type.equals("Long") || type.equals("Int")
                || type.equals("String") || type.equals("Boolean")) {
            type = type.capitalize()
            sb << "\t\t$key = json.opt${type}(\"$key\")" << lineSeparator
        } else {
            //create the Item's JavaBean
            writeSubFiles(key, value)

            //add lines to File
            sb << lineSeparator
            sb << "\t\tval sub${variableNum} = json.optJSONObject(\"${key}\")" << lineSeparator
            sb << "\t\t$key = $type(sub${variableNum})" << lineSeparator
            sb << lineSeparator

            variableNum++
        }

    }

    sb << "\t}" << lineSeparator
    sb << lineSeparator
    sb << "}" << lineSeparator

    if (className.endsWith("Item")) {
        sb << lineSeparator
        sb << "fun create${className}(array: JSONArray) : ArrayList<$className> {" << lineSeparator
        sb << "\tval list = ArrayList<$className>()" << lineSeparator
        sb << "\tval len = array.length()" << lineSeparator
        sb << lineSeparator

        sb << "\tfor(i in 0 until len) {" << lineSeparator
        sb << "\t\tval obj = array.optJSONObject(i)" << lineSeparator
        sb << "\t\tval oneItem = ${className}(obj)" << lineSeparator
        sb << "\t\tlist.add(oneItem)" << lineSeparator
        sb << "\t}" << lineSeparator
        sb << lineSeparator

        sb << "\treturn list" << lineSeparator
        sb << "}" << lineSeparator
    }
    return sb
}

//key参数是防备有JsonArray<自定义类>的， 这样可以给子Item命名
def getType(key, value) {
    if (key.endsWith('id') || key.endsWith('Id')) {
        return 'Long'
    }
    valueType = value.getClass()
    switch (valueType) {
        case 'class java.lang.String':
            return 'String';
        case 'class java.lang.Boolean':
            return 'Boolean';
        case 'class java.lang.Integer':
            return 'Int';
        case 'class java.util.ArrayList':
            listSubClass = value[0].getClass()
            listSubType = getType("${key}Item", value[0])
            return "ArrayList<$listSubType>"
        case 'class groovy.json.internal.LazyMap':
            objectType = key.capitalize();
            return objectType;
    }
}


def writeSubFiles(fkey, fvalue) {
    def sb2 = parseJson(fvalue, fkey.capitalize())
    output("${path}${fkey.capitalize()}.kt", sb2)
}

def output(fileFullName, content) {
    println("szw file = $fileFullName")
    def file = new File(fileFullName)
    file.withWriter { Writer writer ->
        writer.append(content)
    }
}


