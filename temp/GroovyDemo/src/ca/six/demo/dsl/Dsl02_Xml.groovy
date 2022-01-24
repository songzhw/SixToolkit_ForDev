package ca.six.demo.dsl

class MemoDSL {
    static def make(closure) {
        MemoDSL obj = new MemoDSL()
        // any method called in closure will be delegated to the MemoDSL class
        closure.delegate = obj
        closure()
    }

    String result = ""

    def printSelf() {
        println result
    }

    def table(arg){
        result += "table, "
    }

    def td(arg) {
        result += "td"
    }

    def methodMissing(String methodName, args) {
        println "--> $methodName"
        result += "$methodName"
        return result
    }
}

MemoDSL.make {
    table {
        td "one"
        td "two"
    }
    printSelf()
}