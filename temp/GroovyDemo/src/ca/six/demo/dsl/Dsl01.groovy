package ca.six.demo.dsl

class Calculator01 {
    def value = 0
    def add(num) { value += num }
    def clear() { value = 0 }
}

def obj = new Calculator01()
obj.with {
    add(10)
    clear()
    add(5)
    println it.value
}