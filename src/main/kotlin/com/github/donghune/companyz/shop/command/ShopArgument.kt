package com.github.donghune.companyz.shop.command

import com.github.donghune.companyz.shop.model.Shop
import com.github.donghune.companyz.shop.model.ShopRepository
import com.github.monun.kommand.KommandBuilder
import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.suggest
import org.koin.java.KoinJavaComponent.inject

object ShopArgument : KommandArgument<Shop> {

    private val shopRepository by inject<ShopRepository>(ShopRepository::class.java)

    override val parseFailMessage: String
        get() = "${KommandArgument.TOKEN} <-- 해당 상점을 찾지 못했습니다."

    override fun parse(context: KommandContext, param: String): Shop? {
        return shopRepository.get(param)
    }

    override fun suggest(context: KommandContext, target: String): Collection<String> {
        return shopRepository.getList().suggest(target) { it.name }
    }
}

fun KommandBuilder.shop() = ShopArgument