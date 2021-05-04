import evalutation.evalLambda
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
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

        // Comparison operators
        Token.LESS -> evalLess(expression.getParams())
        Token.MORE -> evalMore(expression.getParams())
        Token.EQUAL -> evalEqual(expression.getParams())
        Token.MORE_EQUAL -> evalMoreEqual(expression.getParams())
        Token.LESS_EQUAL -> evalLessEqual(expression.getParams())
        
        else -> {
            // bring lambda to correct format for evaluation
            var current = expression[0]

            if (current is List<*> && current[0] == Token.LAMBDA) {
                current = current as List<Any>
                val params = current[1] as List<Any>
                val method = current[2]

                evalLambda(params, method, expression.getParams())
            } else {
                throw IllegalArgumentException("Invalid token: ${expression[0]}")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun List<*>.getParams() = this.subList(1, this.size) as List<Any>
