fun runFibonacciSequence() {
    while (true) {
        val inputNumber = requestNumber() ?: continue

        printFibonacciContains(inputNumber)
    }
}

private fun requestNumber(): Long? {
    print("Digite o número desejado: ")
    val inputNumber = readln().toLongOrNull()

    if (inputNumber == null) {
        println("Entrada inválida.\n")
        return null
    }

    return inputNumber
}

private fun printFibonacciContains(inputNumber: Long) {
    if (!fibonacciContains(inputNumber)) {
        println("O número não pertence à sequência de Fibonacci.\n")
        return
    }

    println("O número pertence à sequência de Fibonacci.\n")
}

private fun fibonacciContains(inputNumber: Long): Boolean {
    if (inputNumber == 0L) return true

    var currentNumber = 0L
    var nextNumber = 1L
    for (i in 1..inputNumber) {
        val temp = currentNumber + nextNumber
        currentNumber = nextNumber
        nextNumber = temp

        if (nextNumber == inputNumber) return true
        if (nextNumber > inputNumber) return false
    }

    return false
}
