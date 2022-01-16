package ca.six.demo.files.xml

import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.slurpersupport.GPathResult

File xmlFile = new File("../../../../../../res/AndroidManifest.xml")
GPathResult manifest = new XmlSlurper().parse(xmlFile)

manifest.appendNode{
    foo(bar: "1234abc")
}
println XmlUtil.serialize(manifest)