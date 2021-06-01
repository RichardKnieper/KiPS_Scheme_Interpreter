package evalutation

import ENVIRONMENT
import domain.Closure

fun evalClosure(closure: Any, params: List<Any>): Any {
    val expressionToEvaluate = mutableListOf<Any>()
    val evaluatedExpression: Any

    if (closure is Closure) {
        ENVIRONMENT.addFirstClosure(closure)

        expressionToEvaluate.add(closure.method)
        expressionToEvaluate.addAll(params)

        evaluatedExpression = eval(expressionToEvaluate)
        ENVIRONMENT.removeFirstClosure()
    } else {
        expressionToEvaluate.add(closure)
        expressionToEvaluate.addAll(params)
        evaluatedExpression = eval(expressionToEvaluate)
    }

    return evaluatedExpression
}
