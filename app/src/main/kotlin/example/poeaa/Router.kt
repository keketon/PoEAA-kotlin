package example.poeaa

data class Router(
        val method: String,
        val path: String
) {
    fun exec(): Any? {
        return when (path) {
            "/" -> when (method) {
                "GET" -> IndexController().get()
                else -> throw NotImplementedError()
            }
            else -> throw NotImplementedError()
        }
    }
}