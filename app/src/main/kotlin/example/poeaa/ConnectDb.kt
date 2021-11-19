package example.poeaa

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class ConnectDb {
    //    private val MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver"
    private val JDBC_CONNECTION = "jdbc:mysql://localhost:3306/balance_sheet"
    private val USER = "root"
    private val PASS = "password"

    var connection: Connection? = null
    var stmt: Statement? = null
    var rs: ResultSet? = null

    fun select(): Long? {
        var result: Long? = null
        try {
            // load mysql driver
//            Class.forName(MYSQL_DRIVER)
            connection = DriverManager.getConnection(JDBC_CONNECTION, USER, PASS)
            stmt = connection?.createStatement()
            rs = stmt?.executeQuery("SELECT * FROM sample;")
            while (rs?.next() == true) {
                result = rs?.getLong("id");
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            try {
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        return result
    }
}
