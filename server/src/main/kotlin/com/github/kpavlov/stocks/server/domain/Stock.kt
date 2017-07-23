package com.github.kpavlov.stocks.server.domain

data class Stock(val id: Int,
                 val name: String,
                 val price: Long,
                 val timestamp: Long)