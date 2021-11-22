package example.poeaa

import java.sql.Connection
import java.sql.DriverManager

class ConnectionPool private constructor() {
    companion object {
        const val JDBC_CONNECTION = "jdbc:mysql://localhost:3306/balance_sheet"
        const val USER = "root"
        const val PASS = "password"

        private var instance: ConnectionPool? = null

        fun getInstance(): ConnectionPool {
            if (instance == null) {
                instance = ConnectionPool()
            }
            return instance ?: throw RuntimeException()
        }
    }

    private val connections: MutableSet<Connection> = mutableSetOf()

    fun getConnection(): Connection {
        if (connections.isEmpty()) {
            connections.add(DriverManager.getConnection(JDBC_CONNECTION, USER, PASS))
        }

        return connections.iterator().next()
    }

    fun closeAll() {
        connections.onEach(Connection::close).clear()
    }
}