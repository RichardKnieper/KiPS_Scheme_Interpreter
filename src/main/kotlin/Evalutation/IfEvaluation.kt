package evalutation

fun evalIf(params: List<Any>): Any {
    val predicate = eval(params[0]) as Boolean
    return when {
        predicate -> eval(params[1])
        params.size > 3 -> throw IllegalArgumentException("Too many arguments for if statement.")
        params.size < 3 -> ""
        else -> eval(params[2])
    }
}
