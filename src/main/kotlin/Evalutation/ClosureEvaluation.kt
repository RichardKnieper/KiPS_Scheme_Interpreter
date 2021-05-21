package evalutation

import CURRENT_CLOSURE
import domain.Closure

fun evalClosure(closure: Any, params: List<Any>): Any {
    val expressionToEvaluate = mutableListOf<Any>()
    val evaluatedExpression: Any

    if (closure !is Closure) {
        expressionToEvaluate.add(closure)
        expressionToEvaluate.addAll(params)
        evaluatedExpression = eval(expressionToEvaluate)
    } else {
        val oldClosure = CURRENT_CLOSURE
        CURRENT_CLOSURE = closure

        expressionToEvaluate.add(closure.method)
        expressionToEvaluate.addAll(params)

        evaluatedExpression = eval(expressionToEvaluate)
        CURRENT_CLOSURE = oldClosure
    }

    return evaluatedExpression
}
