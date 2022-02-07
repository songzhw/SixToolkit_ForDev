#!/usr/bin/env groovy
println "hello world"

String.metaClass.search = {c ->
  println("hi $delegate")
}

"szw".search() //=> hi szw