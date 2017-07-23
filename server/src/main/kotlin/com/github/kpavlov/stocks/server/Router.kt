package com.github.kpavlov.stocks.server

import akka.http.javadsl.server.Directives.*
import akka.http.javadsl.server.PathMatchers
import akka.http.javadsl.server.Route

object Router {

    internal fun createRoute(): Route {
        return route(
                path(PathMatchers.segment("api").slash("stocks")
                ) {
                    route(
                            get { complete("<h1>Say hello to akka-http GET</h1>") },
                            post { complete("<h1>Say hello to akka-http POST</h1>") }
                    )
                },
                path(PathMatchers.segment("api").slash("stocks").slash(PathMatchers.integerSegment())
                ) { id ->
                    route(
                            get { complete("<h1>Say hello to akka-http GET $id</h1>") },
                            put { complete("<h1>Say hello to akka-http PUT $id </h1>") }
                    )
                },
                pathSingleSlash { getFromResource("static/index.html") }
        )
    }

}
