package ca.six.demo.dsl.example

// 之所以是abstract是因为我们没有实现它的run()方法
abstract class SunScript extends Script {
    Worker obj;

    def login(name) {
        obj.login(name)
    }

    def save(id) {
        obj.save(id)
    }

    def print() {
        obj.print()
    }
}




class Worker {
    String name
    int[] ary

    def login(name) {
        this.name = name
    }

    def save(id) {
        ary += id
    }

    def print() {
        println "$name : $ary"
    }
}