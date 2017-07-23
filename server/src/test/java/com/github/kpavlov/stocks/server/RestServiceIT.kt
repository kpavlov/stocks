package com.github.kpavlov.stocks.server

import akka.actor.ActorSystem
import akka.http.javadsl.Http
import akka.http.javadsl.model.HttpMethods
import akka.http.javadsl.model.HttpRequest
import akka.http.javadsl.model.headers.RawHeader
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

open class RestServiceIT {

    val system = ActorSystem.create("http-client")!!
    val materializer: Materializer = ActorMaterializer.create(system)!!
    val client = Http.get(system)!!

    companion object {

        val server = Server("localhost", 8080)

        @BeforeClass
        @JvmStatic fun startServer() {
            server.start()
        }

        @AfterClass
        @JvmStatic fun stopServer() {
            server.stop()
        }
    }

    @Test
    fun shouldGetStocks() {
        val httpRequest = HttpRequest.create("http://localhost:8080/api/stocks")

        val response = client
                .singleRequest(httpRequest, materializer)
                .toCompletableFuture()
                .get()

        assertEquals(200, response.status().intValue())
        val entity = response.entity()
        assertNotNull(entity.toString())

        println(response)
    }

    @Test
    fun shouldCreateStock() {
        val httpRequest = HttpRequest.create("http://localhost:8080/api/stocks")
                .withEntity("{\"name\": \"XX\",\n" +
                        " \"lastUpdated\":\"2017-01-11T08:49:36.524Z\",\n" +
                        "\"price\":\"1.34\"}")
                .addHeader(RawHeader.create("Content-Type", "application/json"))
                .withMethod(HttpMethods.POST)

        val response = client
                .singleRequest(httpRequest, materializer)
                .toCompletableFuture()
                .get()

        assertEquals(201, response.status().intValue())
        val entity = response.entity()
        assertNotNull(entity.toString())

        println(response)
    }

    @Test
    fun shouldGetOneStock() {
        val getRequest = HttpRequest.create("http://localhost:8080/api/stocks/1")

        val response = client
                .singleRequest(getRequest, materializer)
                .toCompletableFuture()
                .get()

        assertEquals(200, response.status().intValue())
        val entity = response.entity()
        assertNotNull(entity.toString())

        println(response)
    }

    @Test
    fun shouldUpdateStock() {
        val httpRequest = HttpRequest.create("http://localhost:8080/api/stocks/1")
                .withEntity("{\"lastUpdated\":\"2017-01-11T08:49:36.524Z\",\n" +
                        "\"price\":\"1.34\"}")
                .addHeader(RawHeader.create("Content-Type", "application/json"))
                .withMethod(HttpMethods.PUT)


        val response = client
                .singleRequest(httpRequest, materializer)
                .toCompletableFuture()
                .get()

        assertEquals(200, response.status().intValue())
        val entity = response.entity()
        assertNotNull(entity.toString())

        println(response)
    }

}


