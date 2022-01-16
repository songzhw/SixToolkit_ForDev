package ca.six.demo.files.xml

import groovy.xml.Namespace
import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.slurpersupport.GPathResult

// android这个space分散得到处都是, 这明显不好! 跟现有androidManifest不一样!!!
File xmlFile = new File("../../../../../../res/AndroidManifest.xml")
GPathResult manifest = new XmlSlurper().parse(xmlFile)
manifest.declareNamespace(
        android: "http://schemas.android.com/apk/res/android"
)

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

//def fileWriter = new FileWriter("../../../../../res/AndroidManifest.xml")
//XmlUtil.serialize(manifest, fileWriter)
xmlFile.withWriter {writer ->
    XmlUtil.serialize(manifest, writer)
}