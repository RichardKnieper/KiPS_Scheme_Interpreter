package evalutation

import domain.Quote
import domain.Token

@Suppress("UNCHECKED_CAST")
fun evalQuote(params: List<Any>): Quote {
    val debug = when {
        params.size == 1 -> Quote(params[0])
        params.size == 2 && params[0] == Token.APOSTROPHE -> Quote(params)
        else -> throw IllegalArgumentException("Wrong parameters for quote method!")
    }
    return when {
        params.size == 1 -> Quote(params[0])
        params.size == 2 && params[0] == Token.APOSTROPHE -> Quote(params)
        else -> throw IllegalArgumentException("Wrong parameters for quote method!")
    }
}
