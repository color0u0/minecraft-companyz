package com.github.donghune.companyz.transportation.model

import com.github.donghune.namulibrary.extension.emptyLocation
import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File

class TransitPointRepository(
    override val dataType: Class<TransitPoint>,
    override val file: File
) : EntityRepository<TransitPoint>() {
    override fun getDefaultData(key: String): TransitPoint {
        return TransitPoint("", "", listOf(), emptyLocation())
    }

    companion object {
        const val METER_BY_PRICE = 10000
    }
}