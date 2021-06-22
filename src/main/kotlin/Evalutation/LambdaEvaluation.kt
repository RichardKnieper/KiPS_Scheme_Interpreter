@file:Suppress("UNCHECKED_CAST")

package evalutation

import ENVIRONMENT
import domain.Closure
import domain.Token

/**
 * ((lambda (x) (* x x)) 2)
 * params = listOf("x")
 * method = listOf(ADD, "x", "x") OR ("x")
 * input = listOf(2)
 */
fun evalLambda(params: List<String>, methods: List<Any>, returnMethod: Any, input: List<Any>): Any {
    if (params.size != input.size) {
        throw IllegalArgumentException("Too many or too few parameters!")
    }

    if (returnMethod is List<*> && returnMethod.size == 1) {
        // ((lambda (x) (x))2)
        throw IllegalArgumentException("application: not a procedure")
    }

    val returnMethodAsList = if(returnMethod is List<*>) {
        returnMethod as List<Any>
    } else {
        listOf(returnMethod)
    }

    val closureEnv = params.mapIndexed { i, param ->
        param to eval(input[i])
    }.toMap().toMutableMap()

    val closure = Closure(returnMethodAsList, closureEnv, ENVIRONMENT.getFirstClosure())
    ENVIRONMENT.setFirstClosure(closure)

    methods.forEach { eval(it) }

    val output = if (returnMethodAsList.size == 1) {
        eval(returnMethodAsList[0])
    } else {
        eval(returnMethodAsList)
    }

    if(closure.parent == null) {
        ENVIRONMENT.removeFirstClosure()
    } else {
        ENVIRONMENT.setFirstClosure(closure.parent)
    }

    return if(output is List<*> && output[0] == Token.LAMBDA) {
        Closure(output as List<Any>, closureEnv, ENVIRONMENT.getFirstClosure())
    } else {
        output
    }
}
