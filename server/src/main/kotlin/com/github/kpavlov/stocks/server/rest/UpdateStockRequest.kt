package com.github.kpavlov.stocks.server.rest

import java.math.BigDecimal
import java.time.OffsetDateTime

data class UpdateStockRequest(val lastUpdated: OffsetDateTime,
                              val price: BigDecimal)