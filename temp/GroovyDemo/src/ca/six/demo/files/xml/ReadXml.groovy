package ca.six.demo.files.xml

import groovy.xml.Namespace
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult

File xmlFile = new File("../../../../../../res/AndroidManifest.xml")
// GPath, in certain ways, is similar to XPath (that is used for querying XML data)
GPathResult manifest222 = new XmlSlurper().parse(xmlFile)
manifest222.declareNamespace(
        android: "http://schemas.android.com/apk/res/android"
)

// list all the permissions
manifest222."uses-permission".each { p -> println p."@android:name" }

// list activities's path
String pkg = manifest222."@package"  //=> ca.six.demo.advanced2021
manifest222.application.activity.each { actv ->
    String actvName = actv."@android:name"
    println "activity = ${pkg}${actvName}"
}


