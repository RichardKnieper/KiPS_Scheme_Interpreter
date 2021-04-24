fun evalLess(params: List<Any>): Boolean {
    return evalComparisons(params) { a, b -> a < b }
}

fun evalMore(params: List<Any>): Boolean {
    return evalComparisons(params) { a, b -> a > b }
}

fun evalEqual(params: List<Any>): Boolean {
    return evalComparisons(params) { a, b -> a == b }
}

fun evalMoreEqual(params: List<Any>): Boolean {
    return evalComparisons(params) { a, b -> a >= b }
}

fun evalLessEqual(params: List<Any>): Boolean {
    return evalComparisons(params) { a, b -> a <= b }
}

private fun evalComparisons(params: List<Any>, isCorrect: (Double, Double) -> Boolean): Boolean {
    val evaluated = params.map {
        eval(it) as Double
    }

    var n = evaluated[0]
    evaluated.subList(1, evaluated.size).forEach {
        if (isCorrect(n, it))
            n = it
        else
            return false
    }

    return true
}
