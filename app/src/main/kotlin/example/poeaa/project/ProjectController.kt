package example.poeaa.project

import example.poeaa.assignment.usecase.ProjectCostCalculationByTransactionScriptService
import java.util.UUID

class ProjectController {
    fun post(): String {
        return ProjectCostCalculationByTransactionScriptService().calculateProjectCost(
                UUID.fromString("80811c8f-9332-4ab5-9e82-d563e3a20eb2")
        ).toString()
    }
}
