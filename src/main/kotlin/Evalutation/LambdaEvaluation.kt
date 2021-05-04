package evalutation

import eval
import java.lang.IllegalArgumentException

/**
 * ((lambda (x) (* x x)) 2)
 * params = listOf("x")
 * method = listOf(ADD, "x", "x") OR ("x") -> Exception
 * input = listOf(2)
 */
@Suppress("UNCHECKED_CAST")
fun evalLambda(params: List<Any>, method: Any, input: List<Any>): Any {
    if (params.size != input.size) {
        throw IllegalArgumentException("Too many or too few parameters!")
    }

    val methodAsList = if (method is List<*> && method.size == 1) {
        // ((lambda (x) (x))2)
        throw IllegalArgumentException("application: not a procedure")
    } else if (method is List<*>) {
        method as List<Any>
    } else {
        listOf(method)
    }

    val replacedMethod = methodAsList.fillParams(params, input)
    return if (replacedMethod.size == 1) {
        eval(replacedMethod[0])
    } else {
        eval(replacedMethod)
    }
}

private fun List<Any>.fillParams(params: List<Any>, input: List<Any>): List<Any> {
    var replaced = this
    params.forEachIndexed { i, param ->
        replaced = replaced.fillParam(param as String, input[i])
    }
    return replaced
}

@Suppress("UNCHECKED_CAST")
private fun List<Any>.fillParam(param: String, value: Any): List<Any> {
    return this.map {
        when (it) {
            is List<*> -> (it as List<Any>).fillParam(param, value)
            param -> value
            else -> it
        }
    }
}
