package com.github.kpavlov.stocks.server

import akka.http.javadsl.marshallers.jackson.Jackson.unmarshaller
import akka.http.javadsl.model.StatusCodes
import akka.http.javadsl.server.Directives.*
import akka.http.javadsl.server.PathMatchers
import akka.http.javadsl.server.Route
import com.github.kpavlov.stocks.server.rest.CreateStockRequest
import com.github.kpavlov.stocks.server.rest.UpdateStockRequest

object Router {

    internal fun createRoute(): Route {

        val createStockUnmarshaller = unmarshaller(Json.objectMapper(), CreateStockRequest::class.java)
        val updateStockUnmarshaller = unmarshaller(Json.objectMapper(), UpdateStockRequest::class.java)

        return route(
                path(PathMatchers.segment("api").slash("stocks")) {
                    route(
                            get { complete("<h1>Say hello to akka-http GET</h1>") },
                            post {
                                route(
                                        entity(createStockUnmarshaller) { body ->
                                            complete(StatusCodes.CREATED, "<h1>Say hello to akka-http POST</h1><code>$body</code>")
                                        }
                                )


                            }
                    )
                },
                path(PathMatchers.segment("api").slash("stocks").slash(PathMatchers.integerSegment())) { id ->
                    route(
                            get { complete("<h1>Say hello to akka-http GET $id</h1>") },
                            put {
                                entity(updateStockUnmarshaller) { body ->
                                    complete("<h1>Say hello to akka-http PUT $id</h1><code>$body</code>")
                                }
                            }
                    )
                },
                pathSingleSlash { getFromResource("static/index.html") }
        )
    }

}
