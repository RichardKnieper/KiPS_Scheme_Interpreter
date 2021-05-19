package preprocessing

import domain.Token

@Suppress("UNCHECKED_CAST")
fun replace(expression: List<Any>): List<Any> {
    return expression.map {
        if (it !is List<*>) {
            return@map it
        }
        it as List<Any>
        when {
            it[0] == Token.DEFINE && it[1] is List<*> -> it.defineMethodAsLambda()
            it[0] == Token.COND -> it.condAsIf()
            else -> it
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun List<Any>.defineMethodAsLambda(): List<Any> {
    val declaration = this[1] as List<Any>
    val name = declaration[0]
    val variables = declaration.getParams()
    val methods = this.subList(2, this.size).map {
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
private fun List<Any>.condAsIf(): List<Any> {
    val params = this.getParams()

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
private fun List<*>.getParams() = this.subList(1, this.size) as List<Any>
