package example.poeaa.engineer.domain

import example.poeaa.exceptions.DomainException
import example.poeaa.project.domain.ProjectId

data class Engineer(
    val id: EngineerId,
    val name: String,
    val unitPrice: Long,
    val projectsCommit: Map<ProjectId, Long>
) {
    val projects: Set<ProjectId>
        get() = projectsCommit.keys

    init {
        projectsCommit.values.sumOf { it }.also {
            if (it < 100) throw DomainException("Commit is less than 100% (actual = $it %). Engineer: $this")
        }
    }
}
