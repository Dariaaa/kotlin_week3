package nicestring

fun String.isNice(): Boolean {

    val isNotContainsSubstring = listOf("bu", "ba", "be").none { this.contains(it) }

    val isContainsVowels = listOf('a', 'e', 'i', 'o', 'u').sumBy { ch ->
        var c = 0
        this.forEach { if (it == ch) c++ }
        return@sumBy c
    } >= 3

    var isContainDoubleLetter = false

    this.forEachIndexed { index, c ->
        if (index == 0) return@forEachIndexed

        if (c == this[index - 1]) {
            isContainDoubleLetter = true
        }
    }


    return listOf(isNotContainsSubstring, isContainsVowels, isContainDoubleLetter).filter { it }.size >= 2

}

fun main() {
    println("bac".isNice())     //false
    println("aza".isNice())     //false
    println("abaca".isNice())   //false
    println("baaa".isNice())    //true
    println("aaab".isNice())    //true`

}