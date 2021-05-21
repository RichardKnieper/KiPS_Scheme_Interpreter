package preprocessing

import domain.Token
import java.lang.Double.parseDouble

fun tokenize(input: String) = input.preTokenize().parseToList().parseToTokens()

private fun String.preTokenize(): List<Any> {
    val output = mutableListOf<Any>()
    this.split("'()")
        .forEach {
            output.add(it)
            output.add(Token.LIST_END)
        }
    return output.subList(0, output.size - 1)
}

@Suppress("UNCHECKED_CAST")
private fun List<Any>.parseToList(): List<Any> {
    val output = mutableListOf<Any>()
    this.forEach {
        if(it == Token.LIST_END) {
            output.add(it)
        } else {
            output.addAll((it as String).parseToList())
        }
    }
    return output
}

private fun String.parseToList(): List<String> {
    return this.trimIndent()
        .replace("\n", "")
        .replace("(", " ( ")
        .replace(")", " ) ")
        .split(" ")
        .filter { it.isNotEmpty() }
}

private fun List<Any>.parseToTokens(): List<Any> {
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

private fun Any.toToken(): Any {
    return try {
        parseDouble(this as String)
    } catch (e: Exception) {
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

            "cons" -> Token.CONS
            "list" -> Token.LIST
            "car" -> Token.CAR
            "cdr" -> Token.CDR
            "length" -> Token.LENGTH
            "null?" -> Token.IS_LIST_EMPTY

            else -> this
        }
    }
}
