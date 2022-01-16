package ca.six.demo.files.json


import groovy.json.JsonBuilder

// 这个打印出来, 发现只打印detail的, 而id与items没被打印出来, 为什么?
// 原因是chart成了一个Closure<Void>类型, 而不是我期望的hashMap类型
// Groovy中HashMap的字面量定义是 map = [key1: 2, key2: 3]
def chart = {
    id: "2000"
    items: [
            "one", "two", "three"
    ]
    detail: {
        "crossID" "23" //在jsonObject中, 这里就不加":"了. 这个有点奇怪哦!
        "desp" "hi"
    }
}

JsonBuilder builder = new JsonBuilder(chart)
println builder.toPrettyString()

