import java.text.Normalizer

fun runCheckLetterA() {
    while (true) {
        println("\nDigite a string para a verificação: ")
        val inputString = readln().removeAccents()

        printCheckLetterA(inputString)
    }
}

private fun String.removeAccents(): String {
    val newString = Normalizer.normalize(this, Normalizer.Form.NFD)

    return newString.replace("\\p{M}".toRegex(), "")
}

private fun printCheckLetterA(inputString: String) {
    if (!inputString.hasLetterA()) {
        println("A string digitada não contém a letra A.")
        return
    }

    val frequencyLetterA = frequencyLetterA(inputString)
    println("A letra \"a\" aparece $frequencyLetterA vezes.")
}

private fun String.hasLetterA() =
    this.indexOf("a", ignoreCase = true) != -1

private fun frequencyLetterA(inputString: String): Int {
    var counter = 0

    inputString.forEach { word ->
        val wordUppercase = word.uppercase()
        if (wordUppercase == "A") counter++
    }

    return counter
}
