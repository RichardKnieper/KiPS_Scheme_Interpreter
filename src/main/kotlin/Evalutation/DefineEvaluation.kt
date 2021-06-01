package evalutation

import ENVIRONMENT

@Suppress("UNCHECKED_CAST")
fun evalDefine(params: List<Any>) {
    if(params.size != 2) {
        throw IllegalArgumentException("Not the right amount of parameters for define")
    }

    val key = params[0] as String
    val value = eval(params[1])

    ENVIRONMENT.add(key, value)
}
