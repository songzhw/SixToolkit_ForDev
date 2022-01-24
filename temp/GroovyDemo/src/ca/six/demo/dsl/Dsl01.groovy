package ca.six.demo.dsl

class Calculator01 {
    def value = 0
    def add(num) { value += num }
    def clear() { value = 0 }
}

def obj = new Calculator01()
def ret = obj.with {
    it.add(10)
    clear()
    add(5)
    println it.value
    23
}
println ret //=> 23
/*
[比较下kotin中的操作符]

1. T.let( block: (T) -> R) : R, 即参数是T, 但返回的是lambda最后一行
player.let {
    it.setQuality(1080p)
    it.setMute(false)
} //=> 这里返回的lambda最后一行

2. with与let几乎是一样的, 只是成了: with(T) {..},  并且lambda中也可以使用T
源码: with(receiver: T, block: T.()->R) : R
例子:
with(player) {
    setQuality(1080p)
    setMute(false)
}

3. run与with类似, 只不过:
player.run {
    setQuality(1080p)
    setMute(false)
}

4. apply则是返回的T
val playerClone = player.apply {
    setQuality(1080p)
    setMute(false)
}

6. also类似let, 但返回值是T自己
val playerClone = player.also {
    it.setQuality(1080p)
    it.setMute(false)
}
 */