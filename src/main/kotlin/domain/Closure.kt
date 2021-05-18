package domain

class Closure(
    val method: List<Any>,
    val env: MutableMap<String, Any>,
    val parent: Closure?
)
