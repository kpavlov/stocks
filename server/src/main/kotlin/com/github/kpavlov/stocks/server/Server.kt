package com.github.kpavlov.stocks.server

import akka.actor.ActorSystem
import akka.http.javadsl.ConnectHttp
import akka.http.javadsl.Http
import akka.http.javadsl.ServerBinding
import akka.stream.ActorMaterializer
import java.util.concurrent.CompletionStage

class Server(val host: String = "localhost", val port: Int = 8080) {

    val system = ActorSystem.create("http-server")!!
    val http = Http.get(system)!!
    val materializer = ActorMaterializer.create(system)!!

    lateinit var binding: CompletionStage<ServerBinding>

    fun start() {
        println("Starting HTTP Server...")

        val routeFlow = Router.createRoute()
                .seal(system, materializer)
                .flow(system, materializer)

        binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost(host, port), materializer)

        println("Server online at http://$host:$port")
    }

    fun stop() {
        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept { system.terminate() }
    }
}