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
            else -> it
        }
    }
}

@Suppress("UNCHECKED_CAST")
private fun List<Any>.defineMethodAsLambda(): List<Any> {
    val declaration = this[1] as List<Any>
    val name = declaration[0]
    val variables = declaration.subList(1, declaration.size)
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
