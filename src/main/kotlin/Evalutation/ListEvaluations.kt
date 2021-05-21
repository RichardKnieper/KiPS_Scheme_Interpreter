package evalutation

import domain.ListWrapper
import java.lang.IllegalArgumentException

fun evalCar(param: List<Any>) = param.getListWrapper().car()
fun evalCdr(param: List<Any>) = param.getListWrapper().cdr()

fun List<Any>.getListWrapper(): ListWrapper {
    return when {
        this[0] is ListWrapper -> this[0]
        this.size == 1 ->  eval(this[0])
        else -> throw IllegalArgumentException("Wrong number of arguments for car/cdr!")
    } as ListWrapper
}
