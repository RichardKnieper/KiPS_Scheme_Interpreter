import java.lang.Exception

fun main(args: Array<String>) {
    print(">> ")
    var input = readLine().toString()

    while(input != ":q") {
        val tokenized = tokenize(input)

        tokenized.forEach {
            try {
                println(eval(it))
            } catch (e: Exception) {
                println("Something went wrong")
            }
        }

        print(">> ")
        input = readLine().toString()
    }
}
