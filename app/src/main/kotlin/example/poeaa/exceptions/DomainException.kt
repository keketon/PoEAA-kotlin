package example.poeaa.exceptions

class DomainException(
    message: String = "",
    cause: Exception? = null,
) : RuntimeException(message, cause)
