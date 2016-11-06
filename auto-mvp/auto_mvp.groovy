/**@author songzhw */

// TODO 1. viewType -> enum
// TODO 2. view's CF : add the initilization of Presenter
// TODO 3. viewType -> generate Activity, Fragment's detail

// ===================== 1. set basic argumetns =====================
// 1.1  argument that you may want to chagne
pkgName = "ca.six.demo"
coreName = "OrderCancel"
filePath = "E:/tmp"
viewType = "Fragment" // option: Fragment, View, Activity  // TODO -> enum

// separated by ";"
viewMethods = "showOrder;afterCancelOrder;afterRefresh"

// 1.2  argument that you should not change
lineSeparator = System.getProperty("line.separator");


// 1.3  main entrance
println '======================================='
sb = new StringBuilder()
sb = generateIView()
write2File("${filePath}/I${coreName}View.java", sb)

sb = generateView()
write2File("${filePath}/${coreName}${viewType}.java", sb)

sb = generatePresenter()
write2File("${filePath}/${coreName}Presenter.java", sb)

println '======================================='


def write2File(fileFullName, content){
    def file = new File(fileFullName)
    file.withWriter{ Writer writer ->
        writer.append(content)
    }
}


// ===================== 2. generate files for you =====================

// 2.1 generate IView
def generateIView() {
    sb = new StringBuilder()

    // package
    add("package $pkgName;")
    addEmptyLine()

    add("public interface I${coreName}View {")

    methods = viewMethods.split(";")
    methods.each { aMethod ->
        add1("void $aMethod();")
    }

    add("}")
}




// 2.2 generate View
def generateView() {
    sb = new StringBuilder()
    add("package $pkgName;")
    addEmptyLine()

    add("public class ${coreName}${viewType} implements I${coreName}View {")
    addEmptyLine()

    methods = viewMethods.split(";")
    methods.each { method ->
        add1("@Override")
        add1("public void $method() {")
        add2("")
        add1("}")
        addEmptyLine()
    }

    add("}")
}




// 2.3 generate Presenter (,which contains CommonHttpPresenter, restService, Callback)
def generatePresenter() {
    sb = new StringBuilder()
    add("package $pkgName;")
    addEmptyLine()

    add("public class ${coreName}Presenter {")
    addEmptyLine()

    // fields
    add1("private I${coreName}View view;")
    add1("private CommonHttpPresenter httpDelegate;")
    addEmptyLine()

    // constructor
    add1("public ${coreName}Presenter(I${coreName}View view) {")
    add2("this.view = view;")
    addEmptyLine()
    add2("httpDelegate = new CommonHttpPresenter();")
    add1("}")
    addEmptyLine()

    // methods
    methods = viewMethods.split(";")
    methods.each { method ->
        add1("public void $method() {")
        add2("")
        add1("}")
        addEmptyLine()
    }

    // TODO add http callback

    add("}")
}




// ===================== 3. put the files in the right position =====================



// ===================== 4. utils =====================

def addEmptyLine(){
    sb << lineSeparator
}

def add(content){
    sb << content << lineSeparator
}

def add1(content){
    sb << "\t" << content << lineSeparator
}

def add2(content){
    sb << "\t\t" << content << lineSeparator
}


