package ca.six.demo.files

import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult

File xmlFile = new File("../../../../../res/AndroidManifest.xml")
GPathResult manifest222 = new XmlSlurper().parse(xmlFile)

// list all the permissions
manifest222."uses-permission".each { p -> println p."@android:name" }

// list activities's path
String pkg = manifest222."@package"  //=> ca.six.demo.advanced2021
manifest222.application.activity.each { actv ->
    String actvName = actv."@android:name"
    println "activity = ${pkg}${actvName}"
}


