package evalutation

import kotlin.math.round

fun evalAdd(params: List<Any>): Double {
    return params.sumOf {
        eval(it) as Double
    }
}

fun evalSub(params: List<Any>): Double {
    val evaluated = params.map {
        eval(it) as Double
    }
    val minuend = evaluated[0]
    val subtrahend = evaluated.subList(1, evaluated.size).sum()
    return minuend - subtrahend
}

fun evalMultiply(params: List<Any>): Double {
    var prod = eval(params[0]) as Double
    params.subList(1, params.size)
        .forEach {
            prod *= eval(it) as Double
        }
    return prod
}

fun evalDivide(params: List<Any>): Double {
    var dividend = eval(params[0]) as Double
    params.subList(1, params.size)
        .forEach {
            dividend /= eval(it) as Double
        }
    return dividend
}

fun evalRound(params: List<Any>): Double {
    if(params.size != 1) {
        throw IllegalArgumentException("Wrong number of arguments for round: ${params.size}!")
    }
    return round(eval(params[0]) as Double)
}
