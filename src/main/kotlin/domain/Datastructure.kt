package domain

class Datastructure(val list: List<Any>) {
    fun car() = list[0]
    fun cdr() = Datastructure(list.subList(1, list.size))

    override fun toString(): String {
        return if(list.last() == Token.LIST_END) {
            listAsString()
        } else {
            consAsString()
        }
    }

    private fun listAsString(): String {
        return if(list.size == 1) {
            "()"
        } else {
            var s = "("
            list.subList(0, list.size - 1).forEach { s += if (it == Token.LIST_END) "() " else "$it " }
            s.dropLast(1) + ")"
        }
    }

    private fun consAsString(): String {
        var s = "("
        list.subList(0, list.size - 1).forEach { s += if (it == Token.LIST_END) "() " else "$it " }
        s += ". ${list.last()})"
        return s
    }
}
