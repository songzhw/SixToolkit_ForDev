package ca.six.demo.meta

Book.metaClass.sell = { ->
    println "sell itself"
    return pages + 123
}

Book.metaClass.bookName { "$id-$pages"}
Book.metaClass.getP2() { pages }

Book book = new Book()
book.with {
    id = 200
    pages = 300
    author = null
}
println book.sell() //=> 423
println book.bookName() //=> 200 - 300
println book.p2 //=> 300