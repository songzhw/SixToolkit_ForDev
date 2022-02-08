#!/usr/bin/env groovy
println "hello world"

String.metaClass.search = {c ->
  println("hi $delegate")
}

"szw".search() //=> hi szw

def shell = new GroovyShell()
shell.evaluate(new File("Moon.groovy"))