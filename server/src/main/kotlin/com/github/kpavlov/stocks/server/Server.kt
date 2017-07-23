package com.github.kpavlov.stocks.server

import akka.actor.ActorSystem
import akka.http.javadsl.ConnectHttp
import akka.http.javadsl.Http
import akka.http.javadsl.ServerBinding
import akka.stream.ActorMaterializer
import java.util.concurrent.CompletionStage

class Server(val host: String = "localhost", val port: Int = 8080) {

    val system = ActorSystem.create("routes")!!
    val http = Http.get(system)!!
    val materializer = ActorMaterializer.create(system)!!

    lateinit var binding: CompletionStage<ServerBinding>

    fun start() {
        println("Hello World!")

        val routeFlow = Router.createRoute().flow(system, materializer)

        binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost(host, port), materializer)

        println("com.github.kpavlov.stocks.server.Server online at http://$host:$port/\nPress RETURN to stop...")
        System.`in`.read() // let it run until user presses return

        // and shutdown when done
        stop()
    }

    fun stop() {
        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept { system.terminate() }
    }
}