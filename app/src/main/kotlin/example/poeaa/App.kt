/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package example.poeaa

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)
    val tcpConn = ConnectTcp()
    tcpConn.receive()
//    val result = ConnectDb().select() ?: throw IllegalStateException("Result set is null")
//    println("id is: $result")
}
