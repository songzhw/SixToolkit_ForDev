package ca.six.tools

// 1. 坑1: 替换时, 若target里有hashMap<A, B>这样的<>, 或是有[], 这样都会替换失败. 说找不到这样的regex.
//      所以没办法, 我只好使用了"//INSERT1"这样的固定坑位了

/*
[usage]
    $ groovy newActivity.groovy EngagementOfferRecord offers.engagement offers/engagement

: The three parameters are :
        activityName,       packageName,        deepLinkPath

* activityName: If you want to call it "OfferDetailActivity", then the arg is "OfferDetail"
* packageName: it should separate with "." if the package has many levels
* deeplinkPath: If the deeplink of this new activity is "yourcompany://account", then the arg is "account"

it will
* generate `OfferListActivity.kt` file under the `yourcompany/src/main/java/com/xxx/feature/${packageName}` folder
* modify the `AndroidManifest.xml` and `AppSubRoute` files

 */


String[] arguments = this.args

String activityName = arguments[0]
String layoutName = "activity_${camelToUnderScore(activityName)}"

String argPackageName = arguments[1]

def newConstName = camelToUnderScore(activityName).toUpperCase()
String argDeepLinkName = newConstName
if(this.args.length > 2) {
    argDeepLinkName = arguments[2]
}


generateActivity(activityName, layoutName, argPackageName)
generateViewModule(activityName, argPackageName)
generateKoinModuleFile(activityName, argPackageName)
generateSubRoute(newConstName, argDeepLinkName, activityName, argPackageName)
generateXmlFile(argPackageName, activityName)

//  camelCase, PascalCase, under_score_case, kebab-case
private String camelToUnderScore(String camel) {
    String underscore;
    underscore = String.valueOf(Character.toLowerCase(camel.charAt(0))).toLowerCase()
    for (int i = 1; i < camel.length(); i++) {
        underscore += Character.isLowerCase(camel.charAt(i))
                ? String.valueOf(camel.charAt(i))
                : "_" + String.valueOf(Character.toLowerCase(camel.charAt(i)))
    }
    return underscore
}

private void generateActivity(String activityName, String layoutName, String argPackageName) {
    def file = new File("./templates/activity.template")
    def fileContent = file.text
    fileContent = fileContent.replaceAll("##ACTIVITY_NAME##", activityName)
    fileContent = fileContent.replaceAll("##LAYOUT_NAME##", layoutName)
    fileContent = fileContent.replaceAll("##FEATURE_NAME##", argPackageName)

    def packageNameWithSlash = argPackageName.replace(".", "/")
    def destFolder = new File("../yourcompany/src/main/java/com/xxx/feature/${packageNameWithSlash}")
    if(!destFolder.exists()) {
        destFolder.mkdirs()
    }
    def destFile = new File(destFolder, "${activityName}Activity.kt")
    destFile.createNewFile()
    destFile.write(fileContent)
}

private void generateViewModule(String activityName, String argPackageName) {
    def file = new File("./templates/viewModel.template")
    def fileContent = file.text
    fileContent = fileContent.replaceAll("##ACTIVITY_NAME##", activityName)
    fileContent = fileContent.replaceAll("##FEATURE_NAME##", argPackageName)

    def packageNameWithSlash = argPackageName.replace(".", "/")
    def destFolder = new File("../yourcompany/src/main/java/com/xxx/feature/${packageNameWithSlash}")
    if(!destFolder.exists()) {
        destFolder.mkdirs()
    }
    def destFile = new File(destFolder, "${activityName}ViewModel.kt")
    destFile.createNewFile()
    destFile.write(fileContent)
}

private void generateKoinModuleFile(String activityName, String argPackageName) {
    def file = new File("../yourcompany/src/main/java/com/xxx/core/di/koins/FeaturesModule.kt")
    def text = file.text

    def from = "// AUTO-SCRIPT-PLACEHOLDER"
    def to = """$from
    viewModel { ${activityName}ViewModel() }"""
    text = text.replace(from, to)

    def fromImport = "import com.xxx.feature.settings.CommunicationPreferenceViewModel"
    def toImport = """$fromImport
import com.xxx.feature.${argPackageName}.${activityName}ViewModel"""
    text = text.replace(fromImport, toImport)


    file.write(text)
}

private void generateSubRoute(newConstName, String argDeepLinkName, String activityName, String argPackageName) {
    def file = new File("../yourcompany/src/main/java/com/xxx/core/navigation/router/AppSubRoute.kt")

    def newRouteName = "ROUTE_${newConstName}"
    def constTarget = 'const val ROUTE_COMMUNICATION_PREFERENCE = "preferences/notifications"'
    def deeplinkName = """const val ${newRouteName} = "${argDeepLinkName}"
$constTarget
"""

    def mapTarget = "// AUTO-SCRIPT-PLACEHOLDER"
    def mapNewNode = """$mapTarget
\t\tmap[${newRouteName}] = RouteMeta(
\t\t\t${newRouteName},
\t\t\t${activityName}Activity::class.java
\t\t)
"""

    def importTarget = "import com.xxx.feature.settings.CommunicationPreferenceActivity"
    def importNewPackage = """$importTarget
import com.xxx.feature.${argPackageName}.${activityName}Activity
"""


    def newFileContent = file.text.replace(constTarget, deeplinkName)
    newFileContent = newFileContent.replace(mapTarget, mapNewNode)
    newFileContent = newFileContent.replace(importTarget, importNewPackage)
    file.write(newFileContent)
}

private void generateXmlFile(String argPackageName, String activityName) {
    def file = new File("../yourcompany/src/main/AndroidManifest.xml")

    def text = file.text
    def from = """<!-- AUTO-SCRIPT-PLACEHOLDER -->"""
    def to = """$from
        <activity
            android:name="com.xxx.feature.${argPackageName}.${activityName}Activity"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme.Transitions.GreenStatus"
            android:windowSoftInputMode="adjustNothing" />
"""
    text = text.replace(from, to)
    file.write(text)
}

