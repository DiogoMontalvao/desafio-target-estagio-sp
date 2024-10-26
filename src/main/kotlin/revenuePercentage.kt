import java.math.BigDecimal
import java.math.RoundingMode

fun runRevenuePercentage() {
    val statesRevenue = mapOf(
        "SP" to 67836.43,
        "RJ" to 36678.66,
        "MG" to 29229.88,
        "ES" to 27165.48,
        "Outros" to 19849.53
    )

    printStatesPercentage(statesRevenue)
}

private fun printStatesPercentage(statesRevenue: Map<String, Double>) {
    val totalMonthlyRevenue = statesRevenue.values.sum()

    statesRevenue.forEach { (state, revenue) ->
        val stateRevenue = stateRevenue(
            totalMonthlyRevenue,
            revenue
        )

        println("$state possui uma participação de $stateRevenue% no faturamento mensal da distribuidora.")
    }
}

private fun stateRevenue(
    totalMonthlyRevenue: Double,
    revenue: Double
): BigDecimal {
    val percentage = revenue * 100.0 / totalMonthlyRevenue

    return percentage
        .toBigDecimal()
        .setScale(2, RoundingMode.HALF_UP)
}

