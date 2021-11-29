package example.poeaa.engineer.domain

import java.util.UUID

data class EngineerId(val value: UUID) {
    companion object {
        fun fromString(value: String): EngineerId =
                EngineerId(UUID.fromString(value))
    }
}
