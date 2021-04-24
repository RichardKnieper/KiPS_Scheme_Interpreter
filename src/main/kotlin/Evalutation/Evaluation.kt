import java.lang.IllegalArgumentException

fun eval(expression: Any): Any {
    if (expression is Double)
        return expression

    expression as List<*>

    return when (expression[0]) {

        // Math operations
        Token.ADD -> evalAdd(expression.getParams())
        Token.SUBTRACT -> evalSub(expression.getParams())
        Token.MULTIPLY -> evalMultiply(expression.getParams())
        Token.DIVIDE -> evalDivide(expression.getParams())

        else -> throw IllegalArgumentException()
    }
}

private fun List<*>.getParams() = this.subList(1, this.size) as List<Any>
