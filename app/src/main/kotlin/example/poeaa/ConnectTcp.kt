package example.poeaa

import java.io.IOException
import java.net.ServerSocket
import java.nio.charset.Charset

class ConnectTcp {
    fun receive() {
        while (true) {
            try {
                val svSock = ServerSocket(8080)
                val sock = svSock.accept()
                val input = sock.getInputStream()
                val output = sock.getOutputStream()

                var data = ByteArray(1024)
                val readSize = input.read(data)

                //受信データを読み込んだサイズまで切り詰め
                data = data.copyOf(readSize)

                // TODO: various processes
                println("received:\n" + String(data, Charset.forName("UTF-8")) + "\nreceived end.")

                output.write(this.httpRes(0, "application/json"))
                output.flush()

                input.close()
                svSock.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun httpRes(len: Int, type: String): ByteArray {
        val resStr = """
            HTTP/1.1 200 OK
            Connection: close
            Content-Length: $len
            Content-Type: $type
             
        """.trimIndent()

        return resStr.toByteArray()
    }

}