package ca.six.tools

// get arguments from command-line
String argClassName = this.args[0]
String activityName = argClassName
String layoutName = "activity_${camelToUserScore(argClassName)}"

String argPackageName = this.args[1]


def file = new File("../../../../res/Template.src") // 当前目录 (而不是proj根目录)
def fileContent = file.text
fileContent = fileContent.replaceAll("##ACTIVITY_NAME##", activityName)
fileContent = fileContent.replaceAll("##LAYOUT_NAME##", layoutName)
fileContent = fileContent.replaceAll("##FEATURE_NAME##", argPackageName)
println fileContent

def destFile = new File("../../../../output/${argClassName}Activity.kt")
destFile.createNewFile()
destFile.write(fileContent)


//  camelCase, PascalCase, under_score_case, kebab-case
def camelToUserScore(String camel) {
    String underscore;
    underscore = String.valueOf(Character.toLowerCase(camel.charAt(0))).toLowerCase()
    for (int i = 1; i < camel.length(); i++) {
        underscore += Character.isLowerCase(camel.charAt(i))
                ? String.valueOf(camel.charAt(i))
                : "_" + String.valueOf(Character.toLowerCase(camel.charAt(i)))
    }
    return underscore
}