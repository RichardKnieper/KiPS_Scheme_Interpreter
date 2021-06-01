package evalutation

import ENVIRONMENT

fun evalSet(params: List<Any>) {
    if(params.size != 2) {
        throw IllegalArgumentException("Not the right amount of parameters for set!")
    }

    val key = params[0] as String
    val value = eval(params[1])

    if(!ENVIRONMENT.exists(key)) {
        throw IllegalArgumentException("Cannot set variable before its definition: $key")
    }

    ENVIRONMENT.set(key, value)
}
