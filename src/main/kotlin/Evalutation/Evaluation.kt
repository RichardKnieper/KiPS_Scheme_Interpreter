package evalutation

import CURRENT_CLOSURE
import ENVIROMENT
import domain.Closure
import domain.Token

@Suppress("UNCHECKED_CAST")
fun eval(expression: Any): Any {
    if (expression is Double) {
        return expression
    }

    var closure = CURRENT_CLOSURE
    while (closure != null) {
        if(expression in closure.env) {
            return closure.env[expression]!!
        }
        closure = closure.parent
    }

    if (expression in ENVIROMENT.keys) {
        return eval(ENVIROMENT[expression]!!)
    }

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

        in ENVIROMENT.keys -> {
            val expressionToEvaluate = mutableListOf<Any>()
            val variable = ENVIROMENT[expression[0]]!!
            if (variable !is Closure) {
                throw IllegalArgumentException("$variable is not a method")
            }
            CURRENT_CLOSURE = variable

            expressionToEvaluate.add(variable.method)
            expressionToEvaluate.addAll(expression.getParams())

            val evaluatedExpression = eval(expressionToEvaluate)
            CURRENT_CLOSURE = CURRENT_CLOSURE!!.parent
            evaluatedExpression
        }

        else -> {
            var current = expression[0]

            if (current is List<*> && current[0] == Token.LAMBDA) {
                // expression: [[LAMBDA, [x], [MULTIPLY, x, x]], 10]
                // bring lambda to correct format for evaluation
                current = current as List<Any>
                val params = current[1] as List<String>
                val methods = try {
                    current.subList(2, current.size - 2)
                } catch (ex: Exception){
                    listOf()
                }
                val returnMethod = current[current.size - 1]

                evalLambda(params, methods, returnMethod, expression.getParams())
            } else {
                throw IllegalArgumentException("Invalid token: ${expression[0]}")
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun List<*>.getParams() = this.subList(1, this.size) as List<Any>
