// TODO single values
// TODO one values in multiple  directories
// TODO layout
// TODO drawable, menu, 
// TODO multiple layout,drawable, menu in different dir


// ===================== 1. set basic argumetns =====================
projectPath = 'E:/workspace/mine/SixTools'


// ===================== 2. main entrance =====================
// TODO mac is different
// TODO "app" module can be changed

// def commandLint = "cmd /c gradlew.bat :app:lintDebug > a.txt" 
// commandLint.execute(null, new File(projectPath))


lintResultXmlFile = "res/lint_01.xml"  //relative path

def axml = new XmlParser().parse(lintResultXmlFile)

// axml.issue.each {
//     println ("- - - - - - - - - - - - ")
//     println "type = ${it.@id}"
//     println it.@message
//     println it.location.@file
// }

// axml.issue.location.each {
//     println "location = ${it.@file}"
// }

axml.issue.findAll { it.@id == "UnusedResources"}
	.each {
		def resName
		def msg = it.@errorLine1
		def pattern = ~/="(.*)">/ // !!! not the "&quot;"
		msg.eachMatch(pattern){ nodeName -> 
			// print nodeName //=> [ ="app_name2">, app_name2]
			resName = nodeName[1]
		}

		def filePath = new File( it.location.@file[0] ) 
		def resXmlParser = new XmlParser().parse(filePath)
		resXmlParser.children().findAll { resItem ->
			resItem.@name == resName
		}.each{ item -> 
			println item
		}
	}

// http://stackoverflow.com/questions/224926/how-to-insert-move-delete-nodes-in-xml-with-groovy

def tmpXml = new XmlParser().parse("res/colors.xml")
def colors = tmpXml.children()
def toBeDeleted = colors.find { it.@name == 'pinkE56E98' }
colors.remove(toBeDeleted) // remove the nodes from list, not file

new XmlNodePrinter().print(tmpXml)



// ===================== 3. run lint =====================



// ===================== 2. delete unused res =====================



