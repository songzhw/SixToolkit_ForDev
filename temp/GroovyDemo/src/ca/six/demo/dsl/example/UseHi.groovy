package ca.six.demo.dsl.example

// 找不到这个函数!
// foo()

def me = new Me()
me.html {
    me.body {
        me.p {
            "Fun"
        }
    }
}