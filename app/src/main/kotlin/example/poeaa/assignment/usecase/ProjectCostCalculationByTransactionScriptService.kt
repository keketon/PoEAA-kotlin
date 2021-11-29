package example.poeaa.assignment.usecase

import example.poeaa.Gateway
import example.poeaa.exceptions.ApplicationException
import java.util.UUID

class ProjectCostCalculationByTransactionScriptService {
    fun calculateProjectCost(projectId: UUID): Long {
        val assignments = Gateway.getInstance().findAssignmentByProjectId(projectId)
        val engineerIdToCommitPercent = mutableMapOf<UUID, Int>()
        while (assignments.next()) {
            engineerIdToCommitPercent[UUID.fromString(assignments.getString("engineer_id"))] =
                    assignments.getInt("commit_percent")
        }

        val engineers = Gateway.getInstance().findEngineers(engineerIdToCommitPercent.keys)
        var cost = 0L
        while (engineers.next()) {
            val unitPrice = engineers.getLong("unit_price")
            val commitPercent = engineerIdToCommitPercent[UUID.fromString(engineers.getString("id"))]
                    ?: throw ApplicationException()
            // 整数以下が切り捨てられてしまうが、今回はそこまでの厳密さを求めない
            cost += unitPrice * commitPercent / 100L
        }

        Gateway.getInstance().updateProjectCost(id = projectId, cost = cost)
        return cost
    }
}