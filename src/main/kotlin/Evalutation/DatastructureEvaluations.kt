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

fun evalCar(param: List<Any>) = param.asDatastructure().car()
fun evalCdr(param: List<Any>) = param.asDatastructure().cdr()

fun List<Any>.asDatastructure(): Datastructure {
    return when {
        this[0] is Datastructure -> this[0]
        this.size == 1 ->  eval(this[0])
        else -> throw IllegalArgumentException("Wrong number of arguments for car/cdr!")
    } as Datastructure
}
