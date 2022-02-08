#!/usr/bin/env groovy
println "hello world"

String.metaClass.search = {c ->
  println("hi $delegate")
}

"szw".search() //=> hi szw

def args = new Binding()
args.setProperty("name", "szw")
args.setProperty("id", 23)
def shell = new GroovyShell(args)
shell.evaluate(new File("Moon.groovy"))