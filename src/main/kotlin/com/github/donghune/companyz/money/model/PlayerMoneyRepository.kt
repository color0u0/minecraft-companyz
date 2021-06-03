package com.github.donghune.companyz.money.model

import com.github.donghune.companyz.plugin
import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File
import java.util.*

class PlayerMoneyRepository : EntityRepository<PlayerMoney>() {

    override val dataType: Class<PlayerMoney> = PlayerMoney::class.java
    override val file: File = File("${plugin.dataFolder.absolutePath}/money/players")

    override fun getDefaultData(key: String): PlayerMoney {
        return PlayerMoney(
                UUID.fromString(key),
                DEFAULT_MONEY
        )
    }

    companion object {
        const val DEFAULT_MONEY = 3000
    }

}