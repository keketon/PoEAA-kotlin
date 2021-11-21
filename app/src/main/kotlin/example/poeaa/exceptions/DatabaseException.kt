package example.poeaa.exceptions

class DatabaseException(
        message: String = "",
        cause: Exception? = null,
) : RuntimeException(message, cause)
