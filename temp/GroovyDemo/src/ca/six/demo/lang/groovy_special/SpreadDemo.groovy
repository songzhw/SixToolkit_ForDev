package ca.six.demo.lang.groovy_special

import ca.six.demo.Box

// spread operator
Box.apples*.name.each { println it } //=> one, two, three

// GPath
Box.apples.id.each {println it} //=> 1, 2, 3