import java.math.BigDecimal
import java.math.RoundingMode

fun runPercentualFaturamento() {
    val faturamentoEstados = mapOf(
        "SP" to 67836.43,
        "RJ" to 36678.66,
        "MG" to 29229.88,
        "ES" to 27165.48,
        "Outros" to 19849.53
    )

    mostraPercentualPorEstado(faturamentoEstados)
}

private fun mostraPercentualPorEstado(faturamentoEstados: Map<String, Double>) {
    val faturamentoMensal = faturamentoEstados.values.sum()

    faturamentoEstados.forEach { (estado, faturamento) ->
        val percentualPorEstado = percentualPorEstado(
            faturamentoMensal,
            faturamento
        )

        println("$estado possui uma participação de $percentualPorEstado% no faturamento mensal da distribuidora.")
    }
}

private fun percentualPorEstado(
    faturamentoMensal: Double,
    faturamento: Double
): BigDecimal {
    val percentual = faturamento * 100.0 / faturamentoMensal

    return percentual
        .toBigDecimal()
        .setScale(2, RoundingMode.HALF_UP)
}

