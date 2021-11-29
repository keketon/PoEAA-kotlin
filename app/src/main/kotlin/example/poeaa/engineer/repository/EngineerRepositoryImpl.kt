package example.poeaa.engineer.repository

import example.poeaa.Gateway
import example.poeaa.engineer.domain.Engineer
import example.poeaa.engineer.domain.EngineerId
import example.poeaa.engineer.domain.EngineerRepository
import example.poeaa.project.domain.ProjectId

class EngineerRepositoryImpl : EngineerRepository {
    override fun findByIds(ids: Collection<EngineerId>): Set<Engineer> {
        val engineerUuids = ids.map { it.value }
        val engineerResultSet = Gateway.getInstance().findEngineers(engineerUuids)
        val assignmentResultSet = Gateway.getInstance().findAssignmentByEngineerIds(engineerUuids)
        val assignmentMap = ids.associateWith { mutableSetOf<ProjectId>() }
        while (assignmentResultSet.next()) {
            val engineerId = EngineerId.fromString(assignmentResultSet.getString("engineer_id"))
            val projectId = ProjectId.fromString(assignmentResultSet.getString("project_id"))
            assignmentMap[engineerId]?.add(projectId)
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
                        projects = assignmentMap[id] ?: emptySet()
                    )
                )
            }
        }

        return engineers
    }
}