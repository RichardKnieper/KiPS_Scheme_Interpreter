package domain

class StringWrapper(val value: String) {
    override fun toString() = "\"$value\""
}
