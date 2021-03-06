package example.poeaa.project.domain

interface ProjectRepository {
    fun findById(id: ProjectId): Project?
    fun update(project: Project)
}
