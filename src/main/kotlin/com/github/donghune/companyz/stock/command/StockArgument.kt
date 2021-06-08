package com.github.donghune.companyz.stock.command

import com.github.donghune.companyz.stock.model.Stock
import com.github.donghune.companyz.stock.model.StockRepository
import com.github.monun.kommand.KommandBuilder
import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.suggest
import org.koin.java.KoinJavaComponent.inject

object StockArgument : KommandArgument<Stock> {

    private val stockRepository by inject<StockRepository>(StockRepository::class.java)

    override val parseFailMessage: String
        get() = "${KommandArgument.TOKEN} <-- 해당 주식을 찾지 못했습니다."

    override fun parse(context: KommandContext, param: String): Stock? {
        return stockRepository.get(param)
    }

    override fun suggest(context: KommandContext, target: String): Collection<String> {
        return stockRepository.getList().suggest(target) { it.name }
    }
}