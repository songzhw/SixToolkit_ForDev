import groovy.json.JsonSlurper

basicFileName = 'Simple'
requestName = "${basicFileName}Resquest"
parserName  = "${basicFileName}Parser"
responseName = "${basicFileName}Response"
requestFileName =  "${requestName}.java"
parserFileName  =  "${parserName}.java"
responseFileName = "${responseName}.java"
lineSeparator = System.getProperty("line.separator");


def reader = new FileReader('test2.json')
ajson = new JsonSlurper().parse(reader)


println '======================================='
sb = new StringBuilder()
sb = parseJson2RequestFileContent()
write2File(requestFileName, sb)
sb = parseJson2ParserFileContent()
write2File(parserFileName, sb)
sb = parseJson2ResponseFileContent()
write2File(responseFileName, sb)
println '======================================='





def write2File(fileFullName, content){
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
			listSubType = getTypeFromWholePath("${key}OneItem", value[0])
			return "ArrayList<$listSubType>"
		case 'class groovy.json.internal.LazyMap':
			//println "$key -- ${key.getClass()}"
			objectType = key.capitalize();
			return objectType;
			//return 'Object'
	}
}

def parseJson2RequestFileContent(){
	sb = new StringBuilder()
	sb<<"package com.mycompany.requests;"<<lineSeparator
	sb<<lineSeparator

	sb<<"import com.mycompany.common.async_http.AbstractParser;"<<lineSeparator
	sb<<"import com.mycompany.common.async_http.AbstractRequester;"<<lineSeparator
	sb<<"import com.mycompany.common.net.HttpRequestData;"<<lineSeparator
	sb<<lineSeparator

	sb<<"public class ${basicFileName}Request extends AbstractRequester {"<<lineSeparator
	sb<<lineSeparator

	sb<<"\t@Override"<<lineSeparator
	sb<<"\tprotected HttpRequestData createSendData() {"<<lineSeparator
	sb<<"\t\tNTESEpayRequestData request = new NTESEpayRequestData();"<<lineSeparator
	sb<<"\t\trequest.setAction(\"\");"<<lineSeparator
	sb<<"\t\t//request.addRequest()"<<lineSeparator
	sb<<"\t\treturn request;"<<lineSeparator
	sb<<"\t}"<<lineSeparator
	sb<<lineSeparator

	sb<<"\t@Override"<<lineSeparator
	sb<<"\tprotected AbstractParser createParser() {"<<lineSeparator
	sb<<"\t\treturn new ${parserName}();"<<lineSeparator
	sb<<"\t}"<<lineSeparator
	sb<<"}"<<lineSeparator

	//-------------------------------------
}

def parseJson2ParserFileContent(){
	sb = new StringBuilder()
	sb<<"package com.mycompany.requests;"<<lineSeparator
	sb<<lineSeparator

	sb<<"import com.mycompany.common.async_http.AbstractParser;"<<lineSeparator
	sb<<"import com.mycompany.common.async_http.BaseResponse;"<<lineSeparator
	sb<<"import com.mycompany.parser.ResponseParser;"<<lineSeparator
	sb<<lineSeparator

	sb<<"public class ${parserName} extends ResponseParser {"<<lineSeparator
	sb<<lineSeparator

	sb<<"\t@Override"<<lineSeparator
	sb<<"\tprotected AbstractParser createParser() {"<<lineSeparator
	sb<<"\t\treturn null;"<<lineSeparator
	sb<<"\t}"<<lineSeparator
	sb<<lineSeparator

	sb<<"\t@Override"<<lineSeparator
	sb<<"\tprotected BaseResponse parser(String json) {"<<lineSeparator
	sb<<"\t\t$responseName resp = new $responseName(json);"<<lineSeparator
	sb<<"\t\treturn resp;"<<lineSeparator
	sb<<"\t}"<<lineSeparator
	sb<<"}"<<lineSeparator	
}


def parseJson2ResponseFileContent(){
	sb = new StringBuilder()
	sb<<"package com.mycompany.requests;"<<lineSeparator
	sb<<lineSeparator

	sb<<"import android.text.TextUtils;"<<lineSeparator
	sb<<"import com.mycompany.common.async_http.BaseResponse;"<<lineSeparator
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
			sb<<lineSeparator
			


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
	sb2<<"package com.mycompany.model;"<<lineSeparator
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

	write2File("${path}item/${fkey.capitalize()}.java", sb2)
}