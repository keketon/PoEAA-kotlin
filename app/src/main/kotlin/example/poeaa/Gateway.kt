package example.poeaa

import example.poeaa.exceptions.ApplicationException
import example.poeaa.exceptions.DatabaseException
import java.sql.Array
import java.sql.ResultSet
import java.util.UUID

class Gateway private constructor() {
    fun findAssignmentByEmployeeId(employeeId: UUID): ResultSet {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(findAssignmentByEmployeeIdStatement)
        stmt.setString(1, employeeId.toString())
        return stmt.executeQuery()
    }

    fun findAssignmentByProjectId(projectId: UUID): ResultSet {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(findAssignmentByProjectIdStatement)
        stmt.setString(1, projectId.toString())
        return stmt.executeQuery()
    }

    fun findEngineers(engineerIds: Collection<UUID>): ResultSet {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(
                findEngineersStatement.replace("?", List(engineerIds.size) { '?' }.joinToString(","))
        )

        engineerIds.forEachIndexed { index, uuid ->
            stmt.setString(index + 1, uuid.toString())
        }
        return stmt.executeQuery()
    }

    fun updateProject(id: UUID, cost: Long) {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(updateProjectStatement)
        stmt.setLong(1, cost)
        stmt.setString(2, id.toString())

        val updated = stmt.executeUpdate()
        if (updated < 1) throw DatabaseException("Failed to update project {id: $id}")
    }

    companion object {
        private var instance: Gateway? = null
        fun getInstance(): Gateway {
            if (instance == null) {
                instance = Gateway()
            }
            return instance ?: throw RuntimeException()
        }

        private const val findAssignmentByEmployeeIdStatement =
                "SELECT * " +
                        " FROM assignments " +
                        " WHERE engineer_id = ?"
        private const val findAssignmentByProjectIdStatement =
                "SELECT * " +
                        " FROM assignments " +
                        " WHERE project_id = ?"
        private const val findEngineersStatement =
                "SELECT * " +
                        " FROM engineers " +
                        " WHERE id IN (?)"
        private const val updateProjectStatement =
                "UPDATE projects " +
                        " SET cost = ? " +
                        " WHERE id = ?"
    }
}