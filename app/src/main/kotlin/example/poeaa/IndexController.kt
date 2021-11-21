package example.poeaa

class IndexController {
    fun get(): String {
        println("i'm controller!!")
        return """
            <!DOCTYPE html>
            <html lang="ja">
                <body>
                    <p> my first html response!!!
                </body>
            </html>
        """.trimIndent()
    }
}