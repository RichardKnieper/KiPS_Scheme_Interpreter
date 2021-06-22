@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

import domain.Closure
import domain.Environment
import domain.Token
import evalutation.eval
import preprocessing.replace
import preprocessing.tokenize

val ENVIRONMENT = Environment()

fun main() {
    while (true) {
        print(">> ")
        var input = readLine().toString()

        when {
            input == ":q" || input == ":quit" -> break
            input == ":c" || input == ":clear" -> {
                ENVIRONMENT.clear()
                continue
            }

            input.startsWith(":t ") -> {
                val name = input.replace(":t ", "")
                val path = "/testfiles/$name.txt"
                input = try {
                    object {}.javaClass.getResource(path).readText()
                } catch (e: Exception) {
                    println("No test with that name exists.")
                    continue
                }
                println("Test-Code:")
                println(input)
                println("Console output:")
            }
        }

        val tokenized = tokenize(input)
        val replaced = replace(tokenized)

        replaced.forEach {
            try {
                val evaluated = eval(it)
                val toPrint = when (evaluated) {
                    is Closure -> "#<procedure:$it>"
                    Token.LIST_END -> "()"
                    Token.APOSTROPHE -> "quote"
                    is Unit -> return@forEach
                    else -> evaluated
                }
                println(toPrint)
            } catch (e: Exception) {
                println("Something went wrong: ${e.message}")
            }
        }
    }
}
