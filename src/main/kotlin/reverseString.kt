fun runReserveString() {
    while (true) {
        println("\nDigite uma string:")
        val inputString = readln()

        println(inputString.reverseString())
    }
}

private fun String.reverseString(): String {
    val mutableString = this.toMutableList()

    var left = 0
    var right = mutableString.size - 1
    while (left < right) {
        val temp = mutableString[left]

        mutableString[left] = mutableString[right]
        mutableString[right] = temp

        left++
        right--
    }

    return mutableString.joinToString("")
}