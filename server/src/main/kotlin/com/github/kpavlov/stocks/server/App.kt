package com.github.kpavlov.stocks.server

fun main(args: Array<String>) {

    val host = "localhost"
    val port = 8080
    val server = Server(host, port)

    server.start()

    println("com.github.kpavlov.stocks.server.Server online at http://$host:$port/\nPress RETURN to stop...")
    System.`in`.read() // let it run until user presses return

    server.stop()
}