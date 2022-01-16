package ca.six.demo.files.xml.stringsxml

import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.slurpersupport.GPathResult

/*
 写入strings.xml

<resources>
    <string name="app_name">Advanced2021</string>
    <string name="app_id">20221212</string>
</resources>
 */
File xmlFile = new File("../../../../../../../res/strings.xml")
GPathResult resources = new XmlSlurper().parse(xmlFile)

resources.appendNode {
    string(
            name:"description",
            "hello world"
    )
}
xmlFile.withWriter { writer ->
    XmlUtil.serialize(resources, writer)
}