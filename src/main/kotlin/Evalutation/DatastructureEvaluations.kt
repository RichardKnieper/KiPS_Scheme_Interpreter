package evalutation

import domain.Datastructure
import domain.Token

fun evalList(params: List<Any>): Datastructure {
    val evaluatedParams = params.map { eval(it) }
    val list = mutableListOf<Any>()
    list.addAll(evaluatedParams)
    list.add(Token.LIST_END)
    return Datastructure(list)
}

fun evalCons(params: List<Any>): Datastructure {
    if(params.size != 2) {
        throw IllegalArgumentException("Wrong number of parameters for cons!")
    }
    val car = eval(params[0])
    val cdr = eval(params[1])
    val list = mutableListOf<Any>()
    list.add(car)
    if (cdr is Datastructure) {
        list.addAll(cdr.list)
    } else {
        list.add(cdr)
    }
    return Datastructure(list)
}

fun evalLength(params: List<Any>): Double {
    if(params.size != 1) {
        throw IllegalArgumentException("Wrong number of parameters for length!")
    }
    val datastructure = eval(params[0]) as Datastructure
    if (!datastructure.isList()) {
        throw IllegalArgumentException("length needs a list as a parameter!")
    }
    return datastructure.size().toDouble()
}

fun evalIsListEmpty(params: List<Any>): Boolean {
    if(params.size != 1) {
        throw IllegalArgumentException("Wrong number of parameters for null?!")
    }
    val datastructure = eval(params[0]) as Datastructure
    if (!datastructure.isList()) {
        throw IllegalArgumentException("null? needs a list as a parameter!")
    }
    return datastructure.isEmpty()
}

fun evalCar(param: List<Any>) = param.asDatastructure().car()
fun evalCdr(param: List<Any>) = param.asDatastructure().cdr()

fun List<Any>.asDatastructure(): Datastructure {
    return when {
        this[0] is Datastructure -> this[0]
        this.size == 1 ->  eval(this[0])
        else -> throw IllegalArgumentException("Wrong number of arguments for car/cdr!")
    } as Datastructure
}
