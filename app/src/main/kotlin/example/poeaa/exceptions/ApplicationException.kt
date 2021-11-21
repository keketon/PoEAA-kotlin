package example.poeaa.exceptions

class ApplicationException(
        message: String = "",
        cause: Exception? = null,
) : RuntimeException(message, cause)
