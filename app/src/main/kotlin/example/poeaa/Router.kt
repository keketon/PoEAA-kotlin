package example.poeaa

import example.poeaa.assignment.controller.AssignmentController
import example.poeaa.project.ProjectController
import java.sql.Connection
import java.util.UUID

data class Router(
        val method: String,
        val path: String
) {
    fun exec(request: String): Any? {
        return when (path) {
            "/" -> when (method) {
                "GET" -> IndexController().get()
                else -> throw NotImplementedError()
            }
            "/project" -> when (method) {
                "POST" -> ProjectController().post(request)
                else -> throw NotImplementedError()
            }
//            "/assignment" -> when (method) {
//                "GET" -> AssignmentController().get()
//                "POST" -> AssignmentController().post()
//                else -> throw NotImplementedError()
//            }
            else -> throw NotImplementedError()
        }
    }
}