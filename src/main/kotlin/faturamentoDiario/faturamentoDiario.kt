package faturamentoDiario

import com.google.gson.Gson
import com.google.gson.JsonArray
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode

fun runDailyRevenue() {
    val dailyRevenueList = mutableListOf<DailyRevenue>()
    dailyRevenueList.getJSONObjects()

    printRevenueInfo(dailyRevenueList)
}

private data class DailyRevenue(
    val day: Int,
    val value: Double
)

private fun MutableList<DailyRevenue>.getJSONObjects() {
    val gson = Gson()
    try {
        val JSONFile =
            BufferedReader(FileReader("src/main/kotlin/faturamentoDiario/data/dados.json"))
        val jsonArray = gson.fromJson(JSONFile, JsonArray::class.java)
        jsonArray.forEach {
            this.add(
                DailyRevenue(
                    day = it.asJsonObject.get("dia").asInt,
                    value = it.asJsonObject.get("valor").asDouble
                )
            )
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun printRevenueInfo(dailyRevenueList: MutableList<DailyRevenue>) {
    val minDailyRevenue = dailyRevenueList.minDailyRevenue()
    val minValueRevenue = minDailyRevenue.value.formatValue()

    val maxDailyRevenue = dailyRevenueList.maxDailyRevenue()
    val maxValueRevenue = maxDailyRevenue.value.formatValue()

    val daysAboveAverage = dailyRevenueList.daysAboveAverage()

    println("Dia ${minDailyRevenue.day} teve o menor faturamento diário: R$ $minValueRevenue")
    println("Dia ${maxDailyRevenue.day} teve o maior faturamento diário: R$ $maxValueRevenue")
    println("Esse mês teve $daysAboveAverage dias com o faturamento acima da média.")
}

private fun MutableList<DailyRevenue>.minDailyRevenue(): DailyRevenue {
    var minDailyRevenue = this[0]
    var minValueRevenue = this[0].value

    for (dailyRevenue in this) {
        if (dailyRevenue.value <= 0.0) continue

        if (dailyRevenue.value < minValueRevenue) {
            minDailyRevenue = dailyRevenue
            minValueRevenue = dailyRevenue.value
        }
    }

    return minDailyRevenue
}

private fun MutableList<DailyRevenue>.maxDailyRevenue(): DailyRevenue {
    var maxDailyRevenue = this[0]
    var maxValueRevenue = this[0].value

    for (dailyRevenue in this) {
        if (dailyRevenue.value <= 0.0) continue

        if (dailyRevenue.value > maxValueRevenue) {
            maxDailyRevenue = dailyRevenue
            maxValueRevenue = dailyRevenue.value
        }
    }

    return maxDailyRevenue
}

private fun Double.formatValue(): BigDecimal {
    return this.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
}

private fun MutableList<DailyRevenue>.daysAboveAverage(): Int {
    var daysAboveAverage = 0
    val averageDailyRevenue = this.averageDailyRevenue()

    for (dailyRevenue in this) {
        if (dailyRevenue.value <= 0.0) continue

        if (dailyRevenue.value > averageDailyRevenue) daysAboveAverage++
    }

    return daysAboveAverage
}

private fun MutableList<DailyRevenue>.averageDailyRevenue(): Double {
    var revenueDays = 0
    var monthlyRevenue = 0.0

    for (dailyRevenue in this) {
        if (dailyRevenue.value <= 0.0) continue

        revenueDays++
        monthlyRevenue += dailyRevenue.value
    }

    val averageDailyRevenue = monthlyRevenue / revenueDays

    return averageDailyRevenue
}

