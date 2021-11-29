package example.poeaa.project.controller

import com.fasterxml.jackson.databind.ObjectMapper
import example.poeaa.assignment.usecase.ProjectCostCalculationByTransactionScriptService
import java.util.UUID

class ProjectController {
    fun post(request: String): String {
        val mapper = ObjectMapper()
        val requestAsClass = mapper.readValue(request, ProjectRequest::class.java)

        return ProjectCostCalculationByTransactionScriptService().calculateProjectCost(
                UUID.fromString(requestAsClass.projectId)
        ).toString()
    }
}
