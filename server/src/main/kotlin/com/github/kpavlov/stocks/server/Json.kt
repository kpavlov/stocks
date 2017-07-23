package com.github.kpavlov.stocks.server

import com.fasterxml.jackson.databind.ObjectMapper

object Json {

    private val objectMapper  = ObjectMapper().findAndRegisterModules()

    fun objectMapper() : ObjectMapper {
       return objectMapper
    }
}