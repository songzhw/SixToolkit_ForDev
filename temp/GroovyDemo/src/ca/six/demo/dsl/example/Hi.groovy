package ca.six.demo.dsl.example

// kotlin中有顶层函数可被其它类引用; 但groovy中的顶层函数只在本文件中可使用!
def foo(){
    println "foo"
}

class Me {
    def html(Closure c){
        c.call()
    }

    def body(Closure c){
        c.call()
    }

    def p(Closure c){
        def value = c.call()
        println "<p>$value</p>"
    }
}
