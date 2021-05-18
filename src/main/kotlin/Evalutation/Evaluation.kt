package evalutation

import CURRENT_CLOSURE
import ENVIROMENT
import domain.Closure
import domain.Token
import kotlin.math.exp

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

    if (expression in ENVIROMENT) {
        return eval(ENVIROMENT[expression]!!)
    }

    expression as List<Any>

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

        Token.LAMBDA -> Closure(expression, mutableMapOf(), CURRENT_CLOSURE)
        Token.DEFINE -> evalDefine(expression.getParams())
        Token.SET -> evalSet(expression.getParams())

        in ENVIROMENT -> {
            val expressionToEvaluate = mutableListOf<Any>()
            val variable = ENVIROMENT[expression[0]]!!

            if (variable !is Closure) {
                throw IllegalArgumentException("Something went wrong")
            }

            val oldClosure = CURRENT_CLOSURE
            CURRENT_CLOSURE = variable

            expressionToEvaluate.add(variable.method)
            expressionToEvaluate.addAll(expression.getParams())

            val evaluatedExpression = eval(expressionToEvaluate)
            CURRENT_CLOSURE = oldClosure
            evaluatedExpression
        }

        else -> {
            closure = CURRENT_CLOSURE
            while (closure != null) {
                if(expression[0] in closure.env) {
                    val expressionToEvaluate = mutableListOf<Any>()
                    expressionToEvaluate.add(closure.env[expression[0]]!!)
                    expressionToEvaluate.addAll(expression.getParams())
                    return eval(expressionToEvaluate)
                }
                closure = closure.parent
            }

            var current = expression[0]

            if (current is List<*> && current[0] == Token.LAMBDA) {
                // expression: [[LAMBDA, [x], [MULTIPLY, x, x]], 10]
                // bring lambda to correct format for evaluation
                current = current as List<Any>
                val params = current[1] as List<String>
                val methods = try {
                    current.subList(2, current.size - 1)
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
