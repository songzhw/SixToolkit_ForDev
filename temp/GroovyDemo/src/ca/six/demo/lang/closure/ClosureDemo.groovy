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
