package domain

class ListWrapper(private val list: List<Any>) {
    fun car() = list[0]
    fun cdr() = ListWrapper(list.subList(1, list.size))

    override fun toString(): String {
        return if(list.isEmpty()) {
            "()"
        } else {
            var s = "("
            list.forEach { s += "$it " }
            s.dropLast(1) + ")"
        }
    }
}
