import domain.Closure
import domain.Environment
import domain.Token
import evalutation.eval
import preprocessing.replace
import preprocessing.tokenize
import java.io.File

val ENVIRONMENT = Environment()

fun main(args: Array<String>) {
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
                val path = "C:\\Users\\Richard\\IdeaProjects\\KiPS_Scheme_Interpreter\\src\\main\\resources\\testfiles\\$name.txt"
                input = try {
                    File(path).inputStream().bufferedReader().use { it.readText() }
                } catch (e: Exception) {
                    println("No test with that name exists.")
                    continue
                }
                println(input)
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
                    is Unit -> return@forEach
                    else -> evaluated
                }
                println(toPrint)
            } catch (e: Exception) {
//                println("Something went wrong: ${e.message}")
                e.printStackTrace()
                ENVIRONMENT.clear()
            }
        }
    }
}
