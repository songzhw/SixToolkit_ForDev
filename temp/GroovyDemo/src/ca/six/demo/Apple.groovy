package ca.six.demo

class Apple {
    int id
    String name

    Apple(int id, String name) {
        this.id = id
        this.name = name
    }
}


class Box {
    static apples = [new Apple(1, "one"), new Apple(2, "two"), new Apple(3, "three")]
}