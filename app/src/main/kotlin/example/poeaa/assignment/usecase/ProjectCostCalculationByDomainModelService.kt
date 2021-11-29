package example.poeaa.assignment.usecase

import example.poeaa.Gateway
import example.poeaa.engineer.repository.EngineerRepositoryImpl
import example.poeaa.exceptions.ApplicationException
import example.poeaa.project.domain.ProjectId
import example.poeaa.project.repository.ProjectRepositoryImpl
import java.util.UUID

class ProjectCostCalculationByDomainModelService {
    fun calculateProjectCost(projectId: UUID): Long? {
        val project = ProjectRepositoryImpl.getInstance().findById(ProjectId(projectId))
            ?: return null
        val engineers = EngineerRepositoryImpl.getInstance().findByIds(project.engineers)
            .associateBy { it.id }

        val cost = project.engineers.sumOf { engineerId ->
            project.cost * (engineers[engineerId]?.unitPrice ?: throw RuntimeException())
        }

        val newProject = project.updateCost(cost)
        ProjectRepositoryImpl.getInstance().update(newProject)
        return cost
    }
}