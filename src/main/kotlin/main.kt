import domain.Closure
import evalutation.eval
import preprocessing.tokenize
import java.io.File

val ENVIROMENT = mutableMapOf<String, Any>()
var CURRENT_CLOSURE: Closure? = null

fun main(args: Array<String>) {
    while (true) {
        print(">> ")
        var input = readLine().toString()

        when {
            input == ":q" -> break

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

        tokenized.forEach {
            try {
                println(eval(it))
            } catch (e: Exception) {
//                println("Something went wrong")
                e.printStackTrace()
                CURRENT_CLOSURE = null
            }
        }
    }
}
