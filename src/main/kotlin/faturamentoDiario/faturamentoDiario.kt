package faturamentoDiario

import com.google.gson.Gson
import com.google.gson.JsonArray
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.math.RoundingMode

fun runFaturamentoDiario() {
    val listaFaturamentoDiario = mutableListOf<FaturamentoDiario>()
    listaFaturamentoDiario.getJSONObjects()

    mostraInformacoesFaturamento(listaFaturamentoDiario)
}

private data class FaturamentoDiario(
    val dia: Int,
    val valor: Double
)

private fun mostraInformacoesFaturamento(listaFaturamentoDiario: MutableList<FaturamentoDiario>) {
    val menorFaturamentoDiario = listaFaturamentoDiario.menorFaturamentoDiario()
    val valorMenorFaturamento = menorFaturamentoDiario.valor
        .toBigDecimal()
        .setScale(2, RoundingMode.HALF_UP)

    val maiorFaturamentoDiario = listaFaturamentoDiario.maiorFaturamentoDiario()
    val valorMaiorFaturamento = maiorFaturamentoDiario.valor
        .toBigDecimal()
        .setScale(2, RoundingMode.HALF_UP)

    val diasFaturamentoAcimaMedia = listaFaturamentoDiario.diasFaturamentoAcimaMedia()

    println("Dia ${menorFaturamentoDiario.dia} teve o menor faturamento diário: R$ $valorMenorFaturamento")
    println("Dia ${maiorFaturamentoDiario.dia} teve o maior faturamento diário: R$ $valorMaiorFaturamento")
    println("Esse mês teve $diasFaturamentoAcimaMedia dias com o faturamento acima da média.")
}

private fun MutableList<FaturamentoDiario>.getJSONObjects() {
    val gson = Gson()
    try {
        val JSONFile =
            BufferedReader(FileReader("src/main/kotlin/faturamentoDiario/data/dados.json"))
        val jsonArray = gson.fromJson(JSONFile, JsonArray::class.java)
        jsonArray.forEach {
            this.add(
                FaturamentoDiario(
                    dia = it.asJsonObject.get("dia").asInt,
                    valor = it.asJsonObject.get("valor").asDouble
                )
            )
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun MutableList<FaturamentoDiario>.menorFaturamentoDiario(): FaturamentoDiario {
    var menorFaturamentoDiario = this[0]
    var menorValorFaturamento = Double.MAX_VALUE

    for (faturamentoDiario in this) {
        if (faturamentoDiario.valor <= 0.0) {
            continue
        }

        if (faturamentoDiario.valor < menorValorFaturamento) {
            menorFaturamentoDiario = faturamentoDiario
            menorValorFaturamento = faturamentoDiario.valor
        }
    }

    return menorFaturamentoDiario
}

private fun MutableList<FaturamentoDiario>.maiorFaturamentoDiario(): FaturamentoDiario {
    var maiorFaturamentoDiario = this[0]
    var maiorValorFaturamento = 0.0

    for (faturamentoDiario in this) {
        if (faturamentoDiario.valor <= 0.0) {
            continue
        }

        if (faturamentoDiario.valor > maiorValorFaturamento) {
            maiorFaturamentoDiario = faturamentoDiario
            maiorValorFaturamento = faturamentoDiario.valor
        }
    }

    return maiorFaturamentoDiario
}

private fun MutableList<FaturamentoDiario>.diasFaturamentoAcimaMedia(): Int {
    var diasFaturamentoAcimaMedia = 0
    val mediaFaturamentoDiario = this.mediaFaturamentoDiario()

    for (faturamentoDiario in this) {
        if (faturamentoDiario.valor <= 0.0) {
            continue
        }

        if (faturamentoDiario.valor > mediaFaturamentoDiario)
            diasFaturamentoAcimaMedia++
    }

    return diasFaturamentoAcimaMedia
}

private fun MutableList<FaturamentoDiario>.mediaFaturamentoDiario(): Double {
    var diasComFaturamento = 0
    var faturamentoMensal = 0.0

    for (faturamentoDiario in this) {
        if (faturamentoDiario.valor <= 0.0) {
            continue
        }

        diasComFaturamento++
        faturamentoMensal += faturamentoDiario.valor
    }

    val mediaFaturamentoDiario = faturamentoMensal / diasComFaturamento

    return mediaFaturamentoDiario
}

