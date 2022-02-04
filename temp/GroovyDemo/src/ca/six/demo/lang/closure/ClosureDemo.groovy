package ca.six.demo.lang.closure

def myClosure = { println "hi $it"}
myClosure.call("szw") //=> hi szw
myClosure.doCall("zhw")         //=> hi zhw
myClosure("songzhw") //=> hi songzhw