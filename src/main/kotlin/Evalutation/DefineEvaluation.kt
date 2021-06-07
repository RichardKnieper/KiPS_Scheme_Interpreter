package evalutation

import ENVIRONMENT
import domain.Token

@Suppress("UNCHECKED_CAST")
fun evalDefine(params: List<Any>) {
    val value = when {
        params.size == 2 -> eval(params[1])
        params.size == 3 && params[1] == Token.APOSTROPHE -> eval(params.subList(1, params.size))
        else -> throw IllegalArgumentException("Not the right amount of parameters for define")
    }
    val key = params[0] as String

    ENVIRONMENT.add(key, value)
}
