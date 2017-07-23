package com.github.kpavlov.stocks.server

import com.github.kpavlov.stocks.server.domain.Stock

object StocksServiceImpl : StocksService {

    override fun list(): Array<Stock> {
        return arrayOf(
                Stock(1, "A", 100, System.currentTimeMillis()),
                Stock(2, "B", 200, System.currentTimeMillis())
        )
    }
}