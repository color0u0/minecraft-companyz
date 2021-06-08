package com.github.donghune.companyz.work.extension

import com.github.donghune.companyz.work.model.PartTimeJob
import com.github.donghune.companyz.work.model.Work
import com.github.donghune.companyz.work.model.WorkRepository
import com.github.donghune.companyz.work.model.WorkState
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.extension.toMoneyFormat
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent

private val workRepository by KoinJavaComponent.inject<WorkRepository>(WorkRepository::class.java)

fun Work.toPartTimeJob(index: Int): PartTimeJob {
    return PartTimeJob(index, this@toPartTimeJob, WorkState.PENDING, null)
}

fun Work.save() {
    workRepository.save(name)
}