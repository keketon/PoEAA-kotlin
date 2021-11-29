package example.poeaa.project.domain

import example.poeaa.engineer.domain.EngineerId

data class Project(
        val id: ProjectId,
        val name: String,
        val cost: Long,
        val engineers: Set<EngineerId>
) {
    fun updateCost(cost: Long): Project = copy(cost = cost)
}
