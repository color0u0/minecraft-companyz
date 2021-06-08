package com.github.donghune.companyz.work.command

import com.github.donghune.companyz.work.model.Work
import com.github.donghune.companyz.work.model.WorkRepository
import com.github.monun.kommand.KommandBuilder
import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.suggest
import org.koin.java.KoinJavaComponent.inject

object WorkArgument : KommandArgument<Work> {

    private val workRepository by inject<WorkRepository>(WorkRepository::class.java)

    override val parseFailMessage: String
        get() = "${KommandArgument.TOKEN} <-- 해당 상점을 찾지 못했습니다."

    override fun parse(context: KommandContext, param: String): Work? {
        return workRepository.get(param)
    }

    override fun suggest(context: KommandContext, target: String): Collection<String> {
        return workRepository.getList().suggest(target) { it.name }
    }
}

fun KommandBuilder.work() = WorkArgument