package com.github.kpavlov.stocks.server.rest

import java.math.BigDecimal
import java.time.OffsetDateTime

data class CreateStockRequest(val name: String,
                              val lastUpdated: OffsetDateTime,
                              val price: BigDecimal)
