package ca.six.demo.files.json

import groovy.json.JsonSlurper

// [总结]
// 读取json比xml容易多了, 因为不再有attribute, text, namespace这些乱七八糟的, 方便多了
// 转化后, 要么是直接读到值, 要么是jsonObj成了hashMap, 要么是jsonArray成了Arraylist. 转化很方便

File jsonFile = new File("../../../../../../res/package.json")
def pkg = new JsonSlurper().parse(jsonFile)

// read a property
println pkg.name //=> RN101
println pkg.dependencies."@react-native-community/masked-view" //=> ^0.1.6
println "= = = = = = = = = = = = = = = = = = = = = = = ="

// read a JSONObject
println pkg.scripts.getClass()  //=> 类型是: HashMap
pkg.scripts.each { println "key = ${it.key}, value = ${it.value}"}
println "= = = = = = = = = = = = = = = = = = = = = = = ="

// read a JSONArray
println pkg.jest.moduleFileExtensions.getClass() //=> 类型是ArrayList
pkg.jest.moduleFileExtensions.each { println it}