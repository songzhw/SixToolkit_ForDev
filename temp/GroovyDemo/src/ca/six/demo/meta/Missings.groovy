package ca.six.demo.meta

class One {
    def methodMissing(String name, args) {
        println "$name method does not exist"
    }
    def hi() { println "hi" }

    def cache = [:]
    // getter方法
    def propertyMissing(String name) { cache[name] }
    // setter方法
    def propertyMissing(String name, value) { cache[name] = value }
}

def obj = new One()
obj.hi()  //=> hi
obj.hello() //=> hello method does not exist

println obj.foo  //=> null (而不是crash!)
obj.foo = 123
println obj.foo  //=> 123