package domain

@Suppress("UNCHECKED_CAST")
class Quote(private val element: Any): CarCdrAble {
    override fun car() = when {
        element is List<*> && element[0] == Token.APOSTROPHE && element.size == 2 -> Token.APOSTROPHE
        element is List<*> && element[0] == Token.APOSTROPHE -> Quote(element.subList(0, 2))
        element is List<*> && element[0] is List<*> -> Quote(element[0] as List<Any>)
        element is List<*> -> element[0]!!
        else -> throw IllegalArgumentException()
    }

    override fun cdr() = when {
        element is List<*> && element[0] == Token.APOSTROPHE && element.size == 2 -> Quote(element.subList(1, element.size))
        element is List<*> && element[0] == Token.APOSTROPHE -> Quote(element.subList(2, element.size))
        element is List<*> -> Quote(element.subList(1, element.size))
        else -> throw IllegalArgumentException()
    }

    @Suppress("UNCHECKED_CAST")
    override fun toString() = when (element) {
        is List<*> -> (element as List<Any>).asString()
        Token.LIST_END -> "'()"
        else -> element.toString()
    }

    @Suppress("UNCHECKED_CAST")
    private fun List<Any>.asString(): String {
        var s = ""
        this.forEach {
            s += when (it) {
                Token.APOSTROPHE -> "'"
                Token.LIST_END -> "'() "
                is List<*> -> "${(it as List<Any>).asString()} "
                else -> "$it "
            }
        }
        s = s.dropLast(1)
        return if (this.onlyApostropheAndList()) {
            s
        } else {
            "($s)"
        }
    }

    private fun List<Any>.onlyApostropheAndList() = this.size == 2 && this[0] == Token.APOSTROPHE && this[1] is List<*>
}
