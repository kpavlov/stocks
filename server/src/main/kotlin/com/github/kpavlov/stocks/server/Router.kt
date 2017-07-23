package com.github.kpavlov.stocks.server

import akka.http.javadsl.server.Directives.*
import akka.http.javadsl.server.PathMatchers
import akka.http.javadsl.server.Route
import akka.http.javadsl.unmarshalling.Unmarshaller.entityToString

object Router {

    internal fun createRoute(): Route {


        return route(
                path(PathMatchers.segment("api").slash("stocks")
                ) {
                    route(
                            get { complete("<h1>Say hello to akka-http GET</h1>") },
                            post {
                                entity(entityToString()) {
                                    body ->
                                    complete("<h1>Say hello to akka-http POST</h1><code>$body</code>")
                                }
                            }
                    )
                },
                path(PathMatchers.segment("api").slash("stocks").slash(PathMatchers.integerSegment())
                ) { id ->
                    route(
                            get { complete("<h1>Say hello to akka-http GET $id</h1>") },
                            put {
                                entity(entityToString()) {
                                    body ->
                                    complete("<h1>Say hello to akka-http PUT $id</h1><code>$body</code>")
                                }
                            }
                    )
                },
                pathSingleSlash { getFromResource("static/index.html") }
        )
    }

}
