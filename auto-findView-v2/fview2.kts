import java.io.File

val appPkg = "ca.six.demo.advanced2021"
val pkgName = "ca.six.demo.advanced2021.apple"
val activityName = "HelloActivity"
val layoutName = "actv_accessibility"


fun readLayoutXmlFile(fileName: String) {
    var src = File(fileName).readText()
    src = src.removePrefix("""<?xml version="1.0" encoding="utf-8"?>""")

    /*
   1). 第一个(\S*)就是拿到类名.  其中 \S是非空白字符.
        -> 一开始使用 (\S*), 不好. 因为这是两种情况卡不住
           第一种是  </LinearLayout><ImageView id="iv"/>, 会变成id=iv, clazz="/LinearLayout>"
           第二种是注释: <!-- <EditText id="et"/>,  会变成id=et, clazz="!--"
   2). 第二个([^>]*?)就是防备android:id不是第一个属性. 即是View类包与id之前的所有东西. 但不能是">", 若是">", 就表示已经是另一个View了. 这里的[^ab]就是匹配所有除了a也除了e之外的字符
       -> 这主要是针对 <RelativeLayout> <Button id="btn">的情况. 我原来用的是[\s\S]*, 结果就成了 RelativeLayout的id为btn了
   3). ?是非贪婪算法 (默认是贪婪算法)
   4). 正则中的"."是指换行符之外的所有符号. 但我们的View在xml中可能会分多行. 所以第三括号处这里不能用(.*)
   5). "@+id"这样的+号, 在正则中是有意义的. 当我纯粹只想找+号, 那就得用"\+"
   6). 开头的<?xml..>可能会出问题, 即让结果成了类为 "<?xml" , id为tv1
   7). 最后不能加"/>", 因为layout这些是以>结尾的
        也不能是"[\s\S]*", 因为可能有注释 -->也匹配上了
        所以最后要变为"[^-]*"
   => 最终结果就是group[1]是类名, group[4]是id

   那对于:  <TextView android:width="hi" android:id="@+id/tv1" />
   得到的matchResult是个数组, 内容是: [<TextView android:width="hi" android:id="@+id/tv1", TextView,  , android:width="hi" , tv1]
    */
    val regex = """<([^!/\s]*)([^>]*?)android:id="@\+id/(.*?)"([^-]*?)>""".toRegex()
    val matchResult = regex.findAll(src)

    matchResult.map { it.groupValues }
        .forEach {
            var clazz = it[1]
            if (clazz.contains(".")) {
                imported.add("import $clazz")
                clazz = clazz.substringAfterLast(".")
            }
            val id = it[3]
            if (!id.endsWith("_x")) {
                analyzed.put(key = id, value = clazz)
//                println(it)
//                println("$id || $clazz")
            }
        }
    imported.add("import ${appPkg}.R")
}

fun snakeToCamel(snake: String): String {
    val result = StringBuilder()
    snake.split("_").forEachIndexed { index, sub ->
        val camelSub = sub[0].uppercaseChar() + sub.substring(1)
        val name = if (index == 0) sub else camelSub
        result.append(name)
    }
    return result.toString()
}

fun generateFile(): String {
    val importLines = imported.joinToString("\n")

    val declareLines = analyzed.map { (key, value) ->
        "\tlateinit var ${snakeToCamel(key)}: $value"
    }.joinToString("\n")

    val initLines = analyzed.map { (key, _) ->
        "\t\t${snakeToCamel(key)} = findViewById(R.id.$key)"
    }.joinToString("\n")

    var template = File("./TEMPLATE.txt").readText()
    template = template.replace("**packageName**", pkgName)
    template = template.replace("**ActivityClass**", activityName)
    template = template.replace("**layoutName**", layoutName)
    template = template.replace("**imports**", importLines)
    template = template.replace("**InitVar**", declareLines)
    template = template.replace("**FindViews**", initLines)
    return template
}

fun generateTargetFile(): File {
    val folder = File(activityPath)
    folder.mkdirs()

    val name = "${activityName}.kt"
    return File(folder, name)
}

fun pkgToPath(pkg: String) = pkg.split(".").joinToString("/")

// = = = = = = = = = main method = = = = = = = = =
val analyzed = hashMapOf<String, String>()
val imported = arrayListOf<String>()

val layoutPath = "../app/src/main/res/layout/$layoutName.xml"
val activityPath = "../app/src/main/java/${pkgToPath(pkgName)}"

readLayoutXmlFile(layoutPath)
val fileContent = generateFile()
generateTargetFile().writeText(fileContent)
