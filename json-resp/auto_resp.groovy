package tool

/**
 * Created by hzsongzhengwang on 2015/6/10.
 */

 // 使用指南，要预告建立好${path}目录和“${path}/item目录”
 
import groovy.json.JsonSlurper

//========== !!! test2.json里不能带中文 !!! =============
//========== !!! test2.json里不能带中文 !!! =============
//========== !!! test2.json里不能带中文 !!! =============
//========== !!! test2.json里不能带中文 !!! =============

basicFileName = 'SdkPayments'

path = "E:/workspace/mine/android-toolkit/json-resp/output/" //要以 "/" 结尾
responseName = "${basicFileName}Response"
responseFileName = "${path}${responseName}.java"
lineSeparator = System.getProperty("line.separator");

def directory = new File(path)
directory.mkdirs()
def reader = new FileReader('test2.json')
ajson = new JsonSlurper().parse(reader)


println '======================================='
sb = new StringBuilder()
sb = parseJson2ResponseFileContent()
write2File(responseFileName, sb)
println '======================================='



def write2File(fileFullName, content){
    println("szw file = $fileFullName")
    def file = new File(fileFullName)
    file.withWriter{ Writer writer ->
        writer.append(content)
    }
}

//key参数是防备有JsonArray<自定义类>的， 这样可以给子Item命名
def getTypeFromWholePath(key, value){
    if(key.endsWith('id') || key.endsWith('Id')){
        return 'long'
    }
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


//自定义的类，这里可能会没有import的情况。 这时因为进入类里了才知道要import什么类进来。这个可能要手动加下。
def parseJson2ResponseFileContent(){
    sb = new StringBuilder()
    sb<<"package your.company.requests;"<<lineSeparator
    sb<<lineSeparator

    sb<<"import android.text.TextUtils;"<<lineSeparator
    sb<<"import your.company.BaseResponse;"<<lineSeparator
    sb<<"import java.util.ArrayList;"<<lineSeparator
    sb<<"import org.json.JSONArray;"<<lineSeparator
    sb<<"import org.json.JSONException;"<<lineSeparator
    sb<<"import org.json.JSONObject;"<<lineSeparator
    sb<<lineSeparator

    sb<<"public class ${responseName} extends BaseResponse {"<<lineSeparator
    ajson.each{key, value ->
        def type = getTypeFromWholePath(key, value)
        sb<<"\tpublic $type $key;"<<lineSeparator
    }
    sb<<lineSeparator

    sb<<"\tpublic $responseName (String jsonStr){"<<lineSeparator
    sb<<"\t\tsuper(jsonStr);"<<lineSeparator
    sb<<"\t\tif(!TextUtils.isEmpty(jsonStr)){"<<lineSeparator
    sb<<"\t\t\ttry{"<<lineSeparator
    sb<<"\t\t\t\tJSONObject json = new JSONObject(jsonStr);"<<lineSeparator
    ajson.each{key, value ->
        def type = getTypeFromWholePath(key, value)

        if (type.startsWith("ArrayList")){
            def subtype = ""
            def pattern = ~/ArrayList<(.*)>/
            type.find(pattern){
                subtype = it[1]
            }

            sb<<lineSeparator
            sb<<"\t\t\t\tJSONArray array = json.optJSONArray(\"$key\");"<<lineSeparator


            //println "subtype = $subtype"
            if(subtype.equals("long") || subtype.equals("int")
                    || subtype.equals("String") || subtype.equals("boolean")){
                sb<<"\t\t\t\t$key = new $type();"<<lineSeparator
                sb<<"\t\t\t\tfor(int i = 0; i < array.length(); i++){"<<lineSeparator
                sb<<"\t\t\t\t\t$subtype asub = ($subtype) array.opt(i);"<<lineSeparator
                sb<<"\t\t\t\t\t${key}.add(asub);"<<lineSeparator
                sb<<"\t\t\t\t}"<<lineSeparator

            } else {
                //TODO
                writeItemData2File(subtype, value[0])

                sb<<"\t\t\t\t${key} = ${subtype}.createWithJsonArray(array);"<<lineSeparator
                sb<<"\t\t\t\t"<<lineSeparator

            }

        }


        else if(type.equals("long") || type.equals("int")
                || type.equals("String") || type.equals("boolean")){
            type = type.capitalize()
            sb<<"\t\t\t\t$key = json.opt${type}(\"$key\");"<<lineSeparator
        }


        else {
            //create the Item's JavaBean
            writeItemData2File(key,value)

            //add lines to File
            sb<<lineSeparator
            sb<<"\t\t\t\tJSONObject sub = json.optJSONObject(\"${key}\");"<<lineSeparator
            sb<<"\t\t\t\t$key = new $type(sub);"<<lineSeparator
            sb<<lineSeparator
        }
    }
    sb<<"\t\t\t} catch (JSONException e) {"<<lineSeparator
    sb<<"\t\t\t\te.printStackTrace();"<<lineSeparator
    sb<<"\t\t\t}"<<lineSeparator
    sb<<"\t\t}"<<lineSeparator
    sb<<"\t}"<<lineSeparator
    sb<<lineSeparator
    sb<<"}"<<lineSeparator
}


def writeItemData2File(fkey, fvalue){
    def sb2 = new StringBuilder()
    sb2<<"package your.company.model;"<<lineSeparator
    sb2<<lineSeparator

    sb2<<"import java.util.ArrayList;"<<lineSeparator
    sb2<<"import org.json.JSONArray;"<<lineSeparator
    sb2<<"import org.json.JSONException;"<<lineSeparator
    sb2<<"import org.json.JSONObject;"<<lineSeparator
    sb2<<lineSeparator

    sb2<<"public class ${fkey.capitalize()} {"<<lineSeparator
    fvalue.each{key, value ->
        def type = getTypeFromWholePath(key, value)
        sb2<<"\tpublic $type $key;"<<lineSeparator
    }
    sb2<<lineSeparator

    sb2<<"\tpublic ${fkey.capitalize()} (JSONObject json){"<<lineSeparator
    sb2<<"\t\tif(json != null){"<<lineSeparator
    fvalue.each{key, value ->
        def type = getTypeFromWholePath(key, value)
        sb2<<"\t\t\t${key} = json.opt${type.capitalize()}(\"${key}\");"<<lineSeparator
    }
    sb2<<"\t\t}"<<lineSeparator
    sb2<<"\t}"<<lineSeparator
    sb2<<lineSeparator

    sb2<<"\tpublic static ArrayList<${fkey.capitalize()}> createWithJsonArray(JSONArray array) {"<<lineSeparator
    sb2<<"\t\tif(array != null){"<<lineSeparator
    sb2<<"\t\t\tint len = array.length();"<<lineSeparator
    sb2<<"\t\t\tArrayList<${fkey.capitalize()}> list = new ArrayList<${fkey.capitalize()}>();"<<lineSeparator
    sb2<<"\t\t\tfor(int i = 0 ; i < len ; i++){"<<lineSeparator
    sb2<<"\t\t\t\tJSONObject obj = array.optJSONObject(i);"<<lineSeparator
    sb2<<"\t\t\t\t${fkey.capitalize()} oneItem = new ${fkey.capitalize()}(obj);"<<lineSeparator
    sb2<<"\t\t\t\tlist.add(oneItem);"<<lineSeparator
    sb2<<"\t\t\t}"<<lineSeparator
    sb2<<"\t\t\treturn list;"<<lineSeparator
    sb2<<"\t\t}"<<lineSeparator
    sb2<<"\t\treturn null;"<<lineSeparator
    sb2<<"\t}"<<lineSeparator
    sb2<<lineSeparator


    sb2<<"}"

    write2File("${path}${fkey.capitalize()}.java", sb2)
}