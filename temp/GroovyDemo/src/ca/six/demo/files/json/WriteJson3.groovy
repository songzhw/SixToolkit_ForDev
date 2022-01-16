package ca.six.demo.files.json


import groovy.json.JsonBuilder

// 本例成功地修复了WriteJson2的问题
def chart = [
        id    : "2000",
        items : ["one", "two", "three"],
        detail: [
                "crossID" : "23",
                "desp"    : "hi",
                "customer": [
                        "name": "Jane"
                ]
        ]
]

JsonBuilder builder = new JsonBuilder(chart)
println builder.toPrettyString()

