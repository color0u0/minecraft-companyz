package com.github.donghune.companyz.stock.model

import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File

class StockRepository(
    override val dataType: Class<Stock>,
    override val file: File
) : EntityRepository<Stock>() {

    override fun getDefaultData(key: String): Stock {
        return Stock(key, STARTING_PRICE, 0)
    }

    companion object {
        const val STARTING_PRICE = 3000
    }

}