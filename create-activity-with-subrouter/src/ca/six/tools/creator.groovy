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



def file = new File("../../../../res/subroute.template")

def constTarget = 'const val ROUTE_COMMUNICATION_PREFERENCE = "preferences/notifications"'
def deeplinkName = """
const val ROUTE_${newConstName} = "${argDeepLinkName}"
$constTarget
"""

def mapTarget = """\toverride fun registerSubRoute(map: HashMap<String, RouteMeta>) {"""
def mapNewNode = """
$mapTarget
\t\tmap[${newConstName}] = RouteMeta(
\t\t\t${newConstName},
\t\t\t${activityName}Activity::class.java
\t\t)
"""

def importTarget = "import com.c51.feature.settings.CommunicationPreferenceActivity"
def importNewPackage = """
$importTarget
import com.c51.feature.${argPackageName}.${activityName}Activity
"""


def newFileContent = file.text.replaceAll(constTarget, deeplinkName)
//newFileContent = newFileContent.replaceAll(mapTarget, mapNewNode)
newFileContent = newFileContent.replaceAll(importTarget, importNewPackage)

def destFile = new File("../../../../output/AppRoute.kt")
destFile.createNewFile()
destFile.write(newFileContent)

// = = = = = =

//  camelCase, PascalCase, under_score_case, kebab-case
private def camelToUserScore(String camel) {
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
