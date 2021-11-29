package example.poeaa.project.repository

import example.poeaa.Gateway
import example.poeaa.engineer.domain.EngineerId
import example.poeaa.project.domain.Project
import example.poeaa.project.domain.ProjectId
import example.poeaa.project.domain.ProjectRepository

class ProjectRepositoryImpl private constructor() : ProjectRepository {
    companion object {
        private var instance: ProjectRepositoryImpl? = null
        fun getInstance(): ProjectRepositoryImpl {
            if (instance == null) {
                instance = ProjectRepositoryImpl()
            }
            return instance ?: throw RuntimeException()
        }
    }

    override fun findById(id: ProjectId): Project? {
        val projectResultSet = Gateway.getInstance().findProject(id.value)
        if (!projectResultSet.next()) {
            return null
        }

        val assignmentResultSet = Gateway.getInstance().findAssignmentByProjectId(id.value)
        val engineerIds = mutableSetOf<EngineerId>()
        while (assignmentResultSet.next()) {
            engineerIds.add(EngineerId.fromString(assignmentResultSet.getString("engineer_id")))
        }

        return projectResultSet.run {
            Project(
                id = id,
                name = getString("name"),
                cost = getLong("cost"),
                engineers = engineerIds
            )
        }
    }

    override fun update(project: Project) {
        project.run {
            Gateway.getInstance().updateProject(id.value, name, cost)
        }

        Gateway.getInstance().deleteAssignment(project.id.value)
        Gateway.getInstance().insertAssignment(project.id.value, project.engineers.map { it.value })
    }
}
