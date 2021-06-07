package evalutation

import ENVIRONMENT
import domain.*

@Suppress("UNCHECKED_CAST")
fun eval(expression: Any): Any {
    when (expression) {
        is String -> return eval(ENVIRONMENT.find(expression))
        is Double -> return expression
        is Boolean -> return expression
        is Datastructure -> return expression
        is Closure -> return expression
        is StringWrapper -> return expression
        is Quote -> return expression
        Token.LIST_END -> return expression
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

        Token.ROUND -> evalRound(expression.getParams())

        Token.LAMBDA -> Closure(expression, mutableMapOf(), ENVIRONMENT.getFirstClosure())
        Token.DEFINE -> evalDefine(expression.getParams())
        Token.SET -> evalSet(expression.getParams())

        Token.IF -> evalIf(expression.getParams())

        Token.CONS -> evalCons(expression.getParams())
        Token.LIST -> evalList(expression.getParams())
        Token.CAR -> evalCar(expression.getParams())
        Token.CDR -> evalCdr(expression.getParams())
        Token.LENGTH -> evalLength(expression.getParams())
        Token.IS_LIST_EMPTY -> evalIsListEmpty(expression.getParams())

        Token.QUOTE -> evalQuote(expression.getParams())
//        Token.APOSTROPHE -> // TODO

        else -> {
            var current = expression[0]

            if(current is String) {
                val savedValue = ENVIRONMENT.find(current)
                return evalClosure(savedValue, expression.getParams())
            }

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
