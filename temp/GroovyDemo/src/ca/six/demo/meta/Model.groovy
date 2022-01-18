package ca.six.demo.meta

class Author {
    long id
    String name
}

class Book {
    long id
    Author author
    int pages

    int getShelfPosition() {
        new Random().nextInt(100) + 1
    }

    void attachReview(String review) {}
}


Book book = new Book()
Author james = new Author(id: 11, name: "James")
book.with {
    id = 2000
    author = james
    pages = 138
}
book.properties.each {println it} // 打印所有属性 (但不包括metaClass)
println "========"

println book.metaClass //=> org.codehaus.groovy.runtime.HandleMetaClass@6e950bcf[groovy.lang.MetaClassImpl@6e950bcf[class ca.six.demo.meta.Book]]
println book.metaClass.hasProperty(book, "name") //=> null
println book.metaClass.hasProperty(book, "author") //=> groovy.lang.MetaBeanProperty@34a875b3
println book.metaClass.hasProperty(book, "pages")  //=> groovy.lang.MetaBeanProperty@4748a0f9
println "========"

book.metaClass.methods.each { println "method : $it"}
/*
自然有toString(), wait(), notify()等java类固有的方法, 其它的就是:
method : public long ca.six.demo.meta.Book.getId()
method : public int ca.six.demo.meta.Book.getPages()
method : public void ca.six.demo.meta.Book.setAuthor(ca.six.demo.meta.Author)
method : public void ca.six.demo.meta.Book.attachReview(java.lang.String)
method : public int ca.six.demo.meta.Book.getShelfPosition()
...
method : public groovy.lang.MetaClass ca.six.demo.meta.Book.getMetaClass()
method : public void ca.six.demo.meta.Book.setMetaClass(groovy.lang.MetaClass)
...

一个是成员们会自动生成getter, setter方法
二是有getMetaClass()方法, setMetaClass()方法
 */
println book.respondsTo("fetchData")  //=> []
println book.respondsTo("attachReview") //=> [public void ca.six.demo.meta.Book.attachReview(java.lang.String)]
println book.respondsTo("attachReview", String) //=> [public void ca.six.demo.meta.Book.attachReview(java.lang.String)]


