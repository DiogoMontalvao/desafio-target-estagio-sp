fun runInverteString() {
    while (true) {
        println("\nDigite uma string:")
        val inputString = readln()

        println(inputString.inverteString())
    }
}

private fun String.inverteString(): String {
    val mutableString = this.toMutableList()

    var i = 0
    var j = mutableString.size - 1
    while (i < j) {
        val temp = mutableString[i]

        mutableString[i] = mutableString[j]
        mutableString[j] = temp

        i++
        j--
    }

    return mutableString.joinToString("")
}