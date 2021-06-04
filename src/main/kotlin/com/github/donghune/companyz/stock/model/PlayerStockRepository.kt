package com.github.donghune.companyz.stock.model

import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File
import java.util.*

class PlayerStockRepository(
    override val dataType: Class<PlayerStock>,
    override val file: File
) : EntityRepository<PlayerStock>() {

    override fun getDefaultData(key: String): PlayerStock {
        return PlayerStock(UUID.fromString(key), mutableMapOf())
    }

}