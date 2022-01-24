package ca.six.demo.dsl

class Calculator01 {
    def value = 0
    def add(num) { value += num }
    def clear() { value = 0 }
}

def obj = new Calculator01()
def ret = obj.with {
    it.add(10)
    clear()
    add(5)
    println it.value
    23
}
println ret //=> 23
/*
kotlin中, (T)->R, 以及T.()->R是不一样的. 主要是lambda参数是不是this
而在groovy中, 这两者是一样的, 你加不加it都可以!
=> 因为groovy中的obj.with{}, 就类似于Kotlin中的 obj.let{it->}, 或是obj.run{}了!
 */