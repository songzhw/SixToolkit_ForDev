package ca.six.demo.lang.closure

def myClosure = { println "hi $it" }
myClosure.call("szw") //=> hi szw
myClosure.doCall("zhw")         //=> hi zhw
myClosure("songzhw") //=> hi songzhw

/*
def ary = [1,2,3]
def p = {String name -> println name}
ary.each p //编译没问题, 但运行时说"MissingMethodException", 即没这样一个string参数的闭包
 */

// 无参
def c1 = { println "hi" }
def c2 = { -> println "hi" } // 可以有"->"的单独出现. 这在其它语言中比较少见.

// 一参
def c3 = { println it }
def c4 = { name -> println name }

// 多参
def c5 = { id, name -> println "$id -- $name" }
def c6 = { String id, String name -> println "$id -- $name" }
// type可带, 可不带

// 默认参数
def c7 = { id = 0, name -> println "$id, $name" }
c7("great") //=> 0, great
// 默认参数(全是同样的类型时)
def c8 = { String id = "-1", String name = "me" -> println "$id, $name" }
c8("one") //=> one, me
// groovy不像kotlin一样, 有 c8(name="szw")这样的功能, 所以只能用c8("-1", "szw")了


// curried parameters
def chinese = { style, meat, vegetable ->
    "$style ($meat, $vegetable)"
}

// curry之后, 即第一参style已经被填好了
def chuang = chinese.curry("四川菜")
// 同样, 第二参style也搞定了
def chicken = chuang.curry("鸡肉")

println chicken("花生") //=> 四川菜 (鸡肉, 花生)
println chuang("猪肉", "大蒜叶子") //=> 四川菜 (猪肉, 大蒜叶子)


// closure
def wrapperFn(fn) {
    def id = 0
    def innerFn = {
        id++
        fn.call(id)
    }
    return innerFn
}

def wrapper = wrapperFn { println it }
wrapper() //=> 1
wrapper() //=> 2
wrapper() //=> 3
// println wrapper.id // 访问不到, 因为Closure对象没这方法. 这个"id"只是具体某一闭包中的局部变量而已


class Closure01 {
    def closure = {
        println this.class.name  //=> Closure01
        println owner.class.name //=> Closure01
        println delegate.class.name //=> Closure01  ||  设置了delegate=this后, 这值就成了ClosureDemo

        def inner = {
            println this.class.name //=> Closure01
            println owner.class.name //=> Closure01$_closure1
            println delegate.class.name //=> Closure01$_closure1
        }
        println "=================="
        inner()
    }
}

println "====================="
def obj = new Closure01()
def c = obj.closure
c()

println "================"
c.delegate = this
c()

