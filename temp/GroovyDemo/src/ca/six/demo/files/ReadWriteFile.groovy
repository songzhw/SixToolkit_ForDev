package ca.six.demo.files

def file = new File(".")
file.eachLine {line ->
    println line
}
