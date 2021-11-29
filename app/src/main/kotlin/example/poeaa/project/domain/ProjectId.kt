package example.poeaa.project.domain

import java.util.UUID

data class ProjectId(val value: UUID) {
    companion object {
        fun fromString(value: String): ProjectId =
                ProjectId(UUID.fromString(value))
    }
}
