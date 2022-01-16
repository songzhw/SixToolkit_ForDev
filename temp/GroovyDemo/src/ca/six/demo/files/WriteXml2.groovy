package ca.six.demo.files

import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.slurpersupport.GPathResult

File xmlFile = new File("../../../../../res/AndroidManifest.xml")
GPathResult manifest = new XmlSlurper().parse(xmlFile)

/*
<activity android:name=".biz.home.HomeActivity"
    android:screenOrientation="portrait" android:configChanges="keyboardHidden|orientation">

 */
manifest.application.appendNode{
    activity(
            "android:name": ".hi.FirstActivity",
            "android:screenOrientation":"portrait",
            "android:configChanges":"keyboardHidden|orientation"
    )
}

println XmlUtil.serialize(manifest)