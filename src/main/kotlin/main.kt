import java.io.File
import java.lang.Exception

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
            } catch (e: Exception) {
//                println("Something went wrong")
                e.printStackTrace()
            }
        }
    }
}
