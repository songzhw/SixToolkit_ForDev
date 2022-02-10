package ca.six.demo.lang.groovy_special

def obj = new Expando()
obj.id = 23
obj.name = "hi"
println obj  //=> {name=hi, id=23}

obj.say = {String s -> println "hi, $s"}
obj.say("szw")  //=> hi. szw
println obj //=> {name=hi, id=23, say=<pkg>.expandos$_run_closure1@30e868be}