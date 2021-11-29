package example.poeaa

import example.poeaa.exceptions.DatabaseException
import java.sql.ResultSet
import java.util.UUID

class Gateway private constructor() {
    fun findAssignmentByEngineerId(engineerId: UUID): ResultSet {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(findAssignmentByEngineerIdStatement)
        stmt.setString(1, engineerId.toString())
        return stmt.executeQuery()
    }

    fun findAssignmentByEngineerIds(engineerIds: Collection<UUID>): ResultSet {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(
            findAssignmentByEngineerIdsStatement.replace("?", List(engineerIds.size) { '?' }.joinToString(","))
        )

        engineerIds.forEachIndexed { index, uuid ->
            stmt.setString(index + 1, uuid.toString())
        }
        return stmt.executeQuery()
    }

    fun findAssignmentByProjectId(projectId: UUID): ResultSet {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(findAssignmentByProjectIdStatement)
        stmt.setString(1, projectId.toString())
        return stmt.executeQuery()
    }

    fun deleteAssignment(projectId: UUID) {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(deleteAssignmentStatement)
        stmt.setString(1, projectId.toString())

        stmt.executeUpdate()
    }

    fun insertAssignment(projectId: UUID, engineerIdsToCommit: Map<UUID, Long>) {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(
            insertAssignmentStatement.replace("?", List(engineerIdsToCommit.size) { "(?, ?, ?)" }.joinToString(","))
        )
        engineerIdsToCommit.onEachIndexed { index, (engineerId, commit) ->
            stmt.setString(3 * index + 1, projectId.toString())
            stmt.setString(3 * index + 2, engineerId.toString())
            stmt.setLong(3 * index + 3, commit)
        }

        stmt.executeUpdate()
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

    fun findProject(projectId: UUID): ResultSet {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(findProjectStatement)
        stmt.setString(1, projectId.toString())
        return stmt.executeQuery()
    }

    fun updateProjectCost(id: UUID, cost: Long) {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(updateProjectCostStatement)
        stmt.setLong(1, cost)
        stmt.setString(2, id.toString())

        val updated = stmt.executeUpdate()
        if (updated < 1) throw DatabaseException("Failed to update project {id: $id}")
    }

    fun updateProject(id: UUID, name: String, cost: Long) {
        val stmt = ConnectionPool.getInstance().getConnection().prepareStatement(updateProjectStatement)
        stmt.setString(1, name)
        stmt.setLong(2, cost)
        stmt.setString(3, id.toString())

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

        private const val findAssignmentByEngineerIdStatement =
            "SELECT * " +
                    " FROM assignments " +
                    " WHERE engineer_id = ?"
        private const val findAssignmentByEngineerIdsStatement =
            "SELECT * " +
                    " FROM assignments " +
                    " WHERE engineer_id IN (?)"
        private const val findAssignmentByProjectIdStatement =
            "SELECT * " +
                    " FROM assignments " +
                    " WHERE project_id = ?"
        private const val deleteAssignmentStatement =
            "DELETE FROM assignments " +
                    " WHERE project_id = ?"
        private const val insertAssignmentStatement =
            "INSERT INTO assignments(project_id, engineer_id, commit_percent) " +
                    " VALUES ?"
        private const val findEngineersStatement =
            "SELECT * " +
                    " FROM engineers " +
                    " WHERE id IN (?)"
        private const val findProjectStatement =
            "SELECT * " +
                    " FROM projects " +
                    " WHERE id = ?"
        private const val updateProjectCostStatement =
            "UPDATE projects " +
                    " SET cost = ? " +
                    " WHERE id = ?"
        private const val updateProjectStatement =
            "UPDATE projects " +
                    " SET name = ?, cost = ? " +
                    " WHERE id = ?"
    }
}