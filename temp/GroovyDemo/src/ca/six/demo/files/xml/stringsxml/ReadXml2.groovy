package ca.six.demo.files.xml.stringsxml

import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult

/*
 读取strings.xml

<resources>
    <string name="app_name">Advanced2021</string>
    <string name="app_id">20221212</string>
</resources>
 */

File xmlFile = new File("../../../../../../../res/strings.xml")
GPathResult resources = new XmlSlurper().parse(xmlFile)

// 读取子结点, 直接写上名字就行
// 读取属性, 那就要在属性名字前加@
// 读取内容, 就要用 text()
resources.string.each {
    println "key = ${it.@name}, value = ${it.text()}"
}
