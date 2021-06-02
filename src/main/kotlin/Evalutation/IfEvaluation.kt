package evalutation

fun evalIf(params: List<Any>): Any {
    val evaluated = eval(params[0])
    val predicate = when(evaluated) {
        is Boolean -> evaluated
        else -> true
    }
    return when {
        predicate -> eval(params[1])
        params.size > 3 -> throw IllegalArgumentException("Too many arguments for if statement.")
        params.size < 3 -> ""
        else -> eval(params[2])
    }
}
