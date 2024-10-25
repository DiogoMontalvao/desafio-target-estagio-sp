fun runSequenciaFibonacci() {
    while (true) {
        val numeroDesejado = solicitaNumero() ?: return

        printPertenceFibonacci(numeroDesejado)
    }
}

private fun solicitaNumero(): Long? {
    print("Digite o número desejado: ")
    val numeroDesejado = readln().toLongOrNull()

    if (numeroDesejado == null) {
        println("Entrada inválida.")
        return null
    }

    return numeroDesejado
}

private fun printPertenceFibonacci(numeroDesejado: Long) {
    if (!pertenceFibonacci(numeroDesejado)) {
        println("O número não pertence à sequência de Fibonacci.\n")
        return
    }

    println("O número pertence à sequência de Fibonacci.\n")
}

private fun pertenceFibonacci(numeroDesejado: Long): Boolean {
    if (numeroDesejado == 0L) return true

    var numeroAtual = 0L
    var proximoNumero = 1L
    for (i in 1..numeroDesejado) {
        val temp = numeroAtual + proximoNumero
        numeroAtual = proximoNumero
        proximoNumero = temp

        if (proximoNumero == numeroDesejado) return true
        if (proximoNumero > numeroDesejado) return false
    }

    return false
}
