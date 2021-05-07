package evalutation

fun evalAdd(params: List<Any>): Double {
    return params.map {
        eval(it) as Double
    }.sum()
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
