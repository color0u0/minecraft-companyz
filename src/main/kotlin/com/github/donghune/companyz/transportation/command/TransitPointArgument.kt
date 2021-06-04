package com.github.donghune.companyz.transportation.command

import com.github.donghune.companyz.transportation.model.TransitPoint
import com.github.donghune.companyz.transportation.model.TransitPointRepository
import com.github.monun.kommand.KommandBuilder
import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.suggest
import org.koin.java.KoinJavaComponent.inject

object TransitPointArgument : KommandArgument<TransitPoint> {

    private val transitPointRepository by inject<TransitPointRepository>(TransitPointRepository::class.java)

    override val parseFailMessage: String
        get() = "${KommandArgument.TOKEN} <-- 해당 지점을 찾지 못했습니다."

    override fun parse(context: KommandContext, param: String): TransitPoint? {
        return transitPointRepository.get(param)
    }

    override fun suggest(context: KommandContext, target: String): Collection<String> {
        return transitPointRepository.getList().suggest(target) { it.name }
    }
}

fun KommandBuilder.transitPoint() = TransitPointArgument