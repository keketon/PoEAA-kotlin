package example.poeaa

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset

class ConnectTcp {
    private val mapper = ObjectMapper()

    fun receive() {
        while (true) {
            // TCP layer
            lateinit var svSock: ServerSocket
            lateinit var sock: Socket
            lateinit var input: InputStream
            lateinit var output: OutputStream

            try {
                svSock = ServerSocket(8080)
                sock = svSock.accept()
                input = sock.getInputStream()
                output = sock.getOutputStream()

                var data = ByteArray(1024)
                val readSize = input.read(data)

                //受信データを読み込んだサイズまで切り詰め
                data = data.copyOf(readSize)

                val dataStr = String(data, Charset.forName("UTF-8")).split("\n")
                println("received:\n${dataStr.joinToString("\n")}\nreceived end.")
                val (httpMethod, path) = dataStr[0].split(" ")
                val response = Router(method = httpMethod, path = path).exec()

                output.write(this.httpRes(type = "text/html; charset=UTF-8", response = response))
                output.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: NotImplementedError) {
                e.printStackTrace()
            } finally {
                input.close()
                output.close()
                svSock.close()
                ConnectionPool.getInstance().closeAll()
            }
        }
    }

    private fun httpRes(type: String, response: Any?): ByteArray {
        val serializedResponse = if (type == "application/json") {
            mapper.writeValueAsString(response)
        } else {
            response as String
        }

        val resStr = """
            HTTP/1.1 200 OK
            Connection: close
            Content-Length: ${serializedResponse.length}
            Content-Type: $type


        """.trimIndent()


        println(resStr + serializedResponse)

        return (resStr + serializedResponse).toByteArray()
    }

}