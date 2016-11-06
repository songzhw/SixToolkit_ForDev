/**@author songzhw */

// ===================== 1. set basic argumetns =====================
// 1.1  argument that you may want to chagne
pkgName = "ca.six.demo"
coreName = "OrderCancel"
filePath = "E:/tmp"
viewType = "Fragment" // option: Fragment, View, Activity  // TODO -> enum


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
    sb <<"001"
}


// 2.2 generate View
def generateView() {
    sb = new StringBuilder()
    sb <<"002"
}


// 2.3 generate Presenter (,which contains CommonHttpPresenter, restService, Callback)
def generatePresenter() {
    sb = new StringBuilder()
    sb <<"003"
}


// ===================== 3. put the files in the right position =====================



