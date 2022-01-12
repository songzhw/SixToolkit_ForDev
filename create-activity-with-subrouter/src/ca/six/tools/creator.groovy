package ca.six.tools

def file = new File(".") // 当前目录 (而不是proj根目录)
println file.absolutePath