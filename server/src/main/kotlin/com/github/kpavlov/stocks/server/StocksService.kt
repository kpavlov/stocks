package com.github.kpavlov.stocks.server

import com.github.kpavlov.stocks.server.domain.Stock

interface StocksService {
    fun list(): Array<Stock>
}