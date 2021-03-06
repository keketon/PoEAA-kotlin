package example.poeaa

import example.poeaa.project.controller.ProjectController

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