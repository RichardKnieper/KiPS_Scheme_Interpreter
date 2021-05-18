import domain.Closure
import domain.Token
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

            input.startsWith(":l ") -> {
                val name = input.replace(":l ", "")
                val path = "C:\\Users\\Richard\\IdeaProjects\\KiPS_Scheme_Interpreter\\src\\main\\resources\\$name"
                input = File(path).inputStream().bufferedReader().use { it.readText() }
            }
        }

        val tokenized = tokenize(input)

        tokenized.forEach {
            try {
                println(eval(it))
                var x = 12
            } catch (e: Exception) {
//                println("Something went wrong")
                e.printStackTrace()
                CURRENT_CLOSURE = null
            }
        }
    }
}
