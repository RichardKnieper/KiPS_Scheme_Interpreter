package preprocessing

import domain.Token
import java.lang.Double.parseDouble
import java.lang.NumberFormatException

fun tokenize(input: String) = input.parseToList().parseToTokens()

private fun String.parseToList(): List<String> {
    return this.trimIndent()
        .replace("\n", "")
        .replace("(", " ( ")
        .replace(")", " ) ")
        .split(" ")
        .filter { it.isNotEmpty() }
}

private fun List<String>.parseToTokens(): List<Any> {
    val stack = mutableListOf<MutableList<Any>>()
    stack.add(0, mutableListOf())
    this.forEach {
        when (it) {
            "(" -> stack.add(0, mutableListOf())
            ")" -> {
                val l = stack.removeAt(0)
                stack[0].add(l)
            }
            else -> stack[0].add(it.toToken())
        }
    }
    return stack[0]
}

private fun String.toToken(): Any {
    return try {
        parseDouble(this)
    } catch (e: NumberFormatException) {
        when(this) {

            // Math operations
            "+" -> Token.ADD
            "-" -> Token.SUBTRACT
            "*" -> Token.MULTIPLY
            "/" -> Token.DIVIDE

            // Comparison operators
            "<" -> Token.LESS
            ">" -> Token.MORE
            "=" -> Token.EQUAL
            ">=" -> Token.MORE_EQUAL
            "<=" -> Token.LESS_EQUAL

            "lambda" -> Token.LAMBDA
            "define" -> Token.DEFINE
            "set!" -> Token.SET

            "if" -> Token.IF
            "cond" -> Token.COND
            "#t" -> true
            "#f" -> false

            "list" -> Token.LIST
            "car" -> Token.CAR
            "cdr" -> Token.CDR

            else -> this
        }
    }
}
