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

    def table(closure) {
        result += "table< "
        closure()
        result += ">"
    }

    def tr(closure) {
        result += "tr["
        closure()
        result += "], "
    }

    def td(text) {
        result += "td($text), "
    }

    def methodMissing(String methodName, args) {
        println "--> $methodName"
        result += "$methodName"
        return result
    }
}

def myTable(closure){
    MemoDSL.make(closure)
}

myTable {
    table {
        tr {
            td "one"
            td "two"
        }
        tr {
            td "twenty"
        }
    }
    printSelf()
} //=> table< tr[td(one), td(two), ], tr[td(twenty), ], >