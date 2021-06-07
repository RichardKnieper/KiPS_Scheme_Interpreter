package evalutation

import domain.Quote
import domain.Token

fun evalQuote(params: List<Any>): Quote {
    return when {
        params.size == 1 -> Quote(params[0].asString())
        params.size == 2 && params[0] == Token.APOSTROPHE -> Quote("'${params[1].asString()}")
        else -> throw IllegalArgumentException("Wrong parameters for quote method!")
    }
}

@Suppress("UNCHECKED_CAST")
private fun Any.asString(): String {
    return when(this) {
        is List<*> -> (this as List<Any>).asString()
        Token.LIST_END -> "'()"
        else -> this.toString()
    }
}

@Suppress("UNCHECKED_CAST")
private fun List<Any>.asString(): String {
    var s = "("
    this.forEach {
        s += it.asString()
        s += " "
    }
    return s.dropLast(1) + ")"
}
