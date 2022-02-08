#!/usr/bin/env groovy

// for the CompilerConfiguration class
import org.codehaus.groovy.control.*

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

CompilerConfiguration config = new CompilerConfiguration()
config.setScriptBaseClass("SunScript")
def shell2 = new GroovyShell(config)
shell2.evaluate(new File("myDSL.groovy"))