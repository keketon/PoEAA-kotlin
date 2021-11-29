package example.poeaa.engineer.repository

import example.poeaa.Gateway
import example.poeaa.engineer.domain.Engineer
import example.poeaa.engineer.domain.EngineerId
import example.poeaa.engineer.domain.EngineerRepository
import example.poeaa.project.domain.ProjectId
import example.poeaa.project.repository.ProjectRepositoryImpl

class EngineerRepositoryImpl private constructor() : EngineerRepository {
    companion object {
        private var instance: EngineerRepositoryImpl? = null
        fun getInstance(): EngineerRepositoryImpl {
            if (instance == null) {
                instance = EngineerRepositoryImpl()
            }
            return instance ?: throw RuntimeException()
        }
    }

    override fun findByIds(ids: Collection<EngineerId>): Set<Engineer> {
        if(ids.isEmpty()) return emptySet()
        val engineerUuids = ids.map { it.value }
        val engineerResultSet = Gateway.getInstance().findEngineers(engineerUuids)
        val assignmentResultSet = Gateway.getInstance().findAssignmentByEngineerIds(engineerUuids)
        val assignmentMap = ids.associateWith { mutableMapOf<ProjectId, Long>() }
        while (assignmentResultSet.next()) {
            val engineerId = EngineerId.fromString(assignmentResultSet.getString("engineer_id"))
            val projectId = ProjectId.fromString(assignmentResultSet.getString("project_id"))
            assignmentMap[engineerId]?.put(projectId, assignmentResultSet.getLong("commit_percent"))
        }

        val engineers = mutableSetOf<Engineer>()
        while (engineerResultSet.next()) {
            engineerResultSet.run {
                val id = EngineerId.fromString(getString("id"))
                engineers.add(
                    Engineer(
                        id = id,
                        name = getString("name"),
                        unitPrice = getLong("unit_price"),
                        projectsCommit = assignmentMap[id] ?: emptyMap()
                    )
                )
            }
        }

        return engineers
    }
}