package ca.six.demo.files.json


import groovy.json.JsonBuilder

// 这个打印出来, 发现只打印detail的, 而id与items没被打印出来, 为什么
def chart = {
    id: "2000" // 注意不用加逗号在最末哦! 加了反而会编译不过!!!
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

