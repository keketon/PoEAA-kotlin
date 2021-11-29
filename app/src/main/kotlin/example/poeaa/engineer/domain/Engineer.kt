package example.poeaa.engineer.domain

import example.poeaa.project.domain.ProjectId

data class Engineer(
        val id: EngineerId,
        val name: String,
        val unitPrice: Long,
        val projects: Set<ProjectId>
)
