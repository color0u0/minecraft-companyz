package com.github.donghune.companyz.shop.model

import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File

class ShopRepository(override val dataType: Class<Shop>, override val file: File) : EntityRepository<Shop>() {
    override fun getDefaultData(key: String): Shop {
        return Shop(key, mutableListOf())
    }
}