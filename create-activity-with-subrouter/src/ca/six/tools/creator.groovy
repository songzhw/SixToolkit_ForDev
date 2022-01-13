package ca.six.tools

// get arguments from command-line
String[] arguments = this.args

String activityName = arguments[0]
String layoutName = "activity_${camelToUserScore(activityName)}"

String argPackageName = arguments[1]

def newConstName = camelToUserScore(activityName).toUpperCase()
String argDeepLinkName = newConstName
if(this.args.length > 2) {
    argDeepLinkName = arguments[2]
}

generateActivity(activityName, layoutName, argPackageName)

generateSubRoute(newConstName, argDeepLinkName, activityName, argPackageName)



// = = = = = =

//  camelCase, PascalCase, under_score_case, kebab-case
private String camelToUserScore(String camel) {
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
    def file = new File("../../../../res/activity.template") // 当前目录 (而不是proj根目录)
    def fileContent = file.text
    fileContent = fileContent.replaceAll("##ACTIVITY_NAME##", activityName)
    fileContent = fileContent.replaceAll("##LAYOUT_NAME##", layoutName)
    fileContent = fileContent.replaceAll("##FEATURE_NAME##", argPackageName)

    def destFile = new File("../../../../output/${activityName}Activity.kt")
    destFile.createNewFile()
    destFile.write(fileContent)
}

private void generateSubRoute(newConstName, String argDeepLinkName, String activityName, String argPackageName) {
    def file = new File("../../../../res/subroute.template")

    def newRouteName = "ROUTE_${newConstName}"
    def constTarget = 'const val ROUTE_COMMUNICATION_PREFERENCE = "preferences/notifications"'
    def deeplinkName = """const val ${newRouteName} = "${argDeepLinkName}"
$constTarget
"""

    def mapTarget = "// INSERT1"
    def mapNewNode = """$mapTarget
\t\tmap[${newRouteName}] = RouteMeta(
\t\t\t${newRouteName},
\t\t\t${activityName}Activity::class.java
\t\t)
"""

    def importTarget = "import com.c51.feature.settings.CommunicationPreferenceActivity"
    def importNewPackage = """$importTarget
import com.c51.feature.${argPackageName}.${activityName}Activity
"""


    def newFileContent = file.text.replace(constTarget, deeplinkName)
    newFileContent = newFileContent.replace(mapTarget, mapNewNode)
    newFileContent = newFileContent.replace(importTarget, importNewPackage)

    def destFile = new File("../../../../output/AppRoute.kt")
    destFile.createNewFile()
    destFile.write(newFileContent)
}

// TODO 1. "//Insert1" need to be added to the AppSubRoute
//