package evalutation

import domain.CarCdrAble
import domain.Datastructure
import domain.Quote

fun evalCar(param: List<Any>) = param.asCarCdrAble().car()
fun evalCdr(param: List<Any>) = param.asCarCdrAble().cdr()

private fun List<Any>.asCarCdrAble(): CarCdrAble {
    val debug = when {
        this[0] is Datastructure || this[0] is Quote -> this[0]
        this.size == 1 ->  eval(this[0])
        else -> throw IllegalArgumentException("Wrong number of arguments for car/cdr!")
    } as CarCdrAble
    val car = debug.car()
    val cdr = debug.cdr()

    return when {
        this[0] is Datastructure || this[0] is Quote -> this[0]
        this.size == 1 ->  eval(this[0])
        else -> throw IllegalArgumentException("Wrong number of arguments for car/cdr!")
    } as CarCdrAble
}
