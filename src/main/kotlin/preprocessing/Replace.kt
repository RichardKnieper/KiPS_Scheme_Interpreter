package preprocessing

import domain.Token

@Suppress("UNCHECKED_CAST")
fun replace(expression: List<Any>): List<Any> {
    fun runReplace(exp: Any, call: (List<Any>) -> List<Any>, condition: (List<Any>) -> Boolean): Any {
        if (exp !is List<*>) {
            return exp
        }
        exp as List<Any>
        if (exp.isEmpty()) {
            return exp
        }

        return (if(condition(exp)) {
            call(exp)
        } else {
            exp
        }).map { runReplace(it, call, condition) }
    }

    var output = runReplace(expression, ::defineMethodAsLambda) {
            exp -> exp[0] == Token.DEFINE && exp[1] is List<*>
    } as List<Any>

    output = runReplace(output, ::condAsIf) {
        exp -> exp[0] == Token.COND
    } as List<Any>

    output = runReplace(output, ::letAsLambda) {
        exp -> exp[0] == Token.LET
    } as List<Any>

    return output
}

@Suppress("UNCHECKED_CAST")
private fun defineMethodAsLambda(list: List<Any>): List<Any> {
    val declaration = list[1] as List<Any>
    val name = declaration[0]
    val variables = declaration.getParams()
    val methods = list.subList(2, list.size).map {
        if (it is List<*>) {
            replace(it as List<Any>)
        } else {
            it
        }
    }
    val lambda = mutableListOf(Token.LAMBDA, variables)
    lambda.addAll(methods)
    return listOf(Token.DEFINE, name, lambda)
}

@Suppress("UNCHECKED_CAST")
private fun condAsIf(list: List<Any>): List<Any> {
    val params = list.getParams()

    var output: MutableList<Any>? = null
    for(i in params.size - 1 downTo 0) {
        val it = params[i] as List<Any>
        val new = mutableListOf(Token.IF, it[0], it[1])
        if (output != null) {
            new.add(output)
        }
        output = new
    }
    return output!!
}

@Suppress("UNCHECKED_CAST")
private fun letAsLambda(list: List<Any>): List<Any> {
    val declerations = (list[1] as List<Any>).associate {
        it as List<Any>
        it[0] to it[1]
    }
    val method = list[2]

    val params = mutableListOf<Any>()
    val input = mutableListOf<Any>()
    declerations.forEach { (key, value) ->
        params.add(key)
        input.add(value)
    }

    val expression = mutableListOf<Any>()
    expression.add(listOf(Token.LAMBDA, params, method))
    expression.addAll(input)
    return expression
}

@Suppress("UNCHECKED_CAST")
private fun List<*>.getParams() = this.subList(1, this.size) as List<Any>
