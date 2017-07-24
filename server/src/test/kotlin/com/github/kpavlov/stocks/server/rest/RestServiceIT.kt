package com.github.kpavlov.stocks.server.rest

import akka.actor.ActorSystem
import akka.http.javadsl.Http
import akka.http.javadsl.model.ContentTypes
import akka.http.javadsl.model.HttpMethods
import akka.http.javadsl.model.HttpRequest
import akka.http.javadsl.model.StatusCodes
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import com.github.kpavlov.stocks.server.Server
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import java.util.*
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

        assertEquals(StatusCodes.OK, response.status())
        val entity = response.entity()
        assertNotNull(entity.toString())

        println(response)
    }

    @Test
    fun shouldCreateStock() {
        val httpRequest = HttpRequest.create("http://localhost:8080/api/stocks")
                .withEntity(ContentTypes.APPLICATION_JSON, "{\"name\": \"XX\",\n" +
                        " \"lastUpdated\":\"2017-01-11T08:49:36.524Z\",\n" +
                        "\"price\":\"1.34\"}")

                .withMethod(HttpMethods.POST)

        val response = client
                .singleRequest(httpRequest, materializer)
                .toCompletableFuture()
                .get()

        assertEquals(StatusCodes.CREATED, response.status())
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

        assertEquals(StatusCodes.OK, response.status())
        val entity = response.entity()
        assertNotNull(entity.toString())

        println(response)
    }

    @Test
    fun shouldUpdateStock() {
        val httpRequest = HttpRequest.create("http://localhost:8080/api/stocks/1")
                .withEntity(ContentTypes.APPLICATION_JSON, "{\"lastUpdated\":\"2017-01-11T08:49:36.524Z\",\n" +
                        "\"price\":\"1.34\"}")
                .withMethod(HttpMethods.PUT)


        val response = client
                .singleRequest(httpRequest, materializer)
                .toCompletableFuture()
                .get()

        assertEquals(StatusCodes.OK, response.status())
        assertEquals(response.entity().contentLengthOption, OptionalLong.of(0))

        println(response)
    }

}


