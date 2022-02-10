package ca.six.demo.lang.groovy_special

import ca.six.demo.Box

// spread operator
Box.apples*.name.each { println it } //=> one, two, three

// GPath
Box.apples.id.each { println it } //=> 1, 2, 3

ArrayList maps = [  ["a": 1, "b": 2]  ,   ["a": "一", "b": "二"]  ]
maps.a.each { println it } //=> 1, "一"