package ca.six.demo.files

import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.slurpersupport.GPathResult

File xmlFile = new File("../../../../../res/AndroidManifest.xml")
GPathResult manifest = new XmlSlurper().parse(xmlFile)

manifest.@"xmlns:android" = "http://schemas.android.com/apk/res/android"
manifest.application.appendNode{
    foo(
            bar: "1234abc",
            "android:name": "abcde",
            2345
    )
}
println XmlUtil.serialize(manifest)
/*
...
    <foo bar="1234abc">2345</foo>
  </application>
</manifest>
 */