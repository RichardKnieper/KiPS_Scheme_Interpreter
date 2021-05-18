package evalutation

import CURRENT_CLOSURE
import ENVIROMENT
import java.lang.IllegalArgumentException

fun evalSet(params: List<Any>) {
    if(params.size != 2) {
        throw IllegalArgumentException("Not the right amount of parameters for set!")
    }

    val key = params[0] as String
    val value = eval(params[1])

    var closure = CURRENT_CLOSURE
    while (closure != null) {
        if(key in closure.env) {
            closure.env[key] = value
            return
        }
        closure = closure.parent
    }

    if (key in ENVIROMENT) {
        ENVIROMENT[key] = value
        return
    }

    throw IllegalArgumentException("Cannot set variable before its definition: $key")
}
