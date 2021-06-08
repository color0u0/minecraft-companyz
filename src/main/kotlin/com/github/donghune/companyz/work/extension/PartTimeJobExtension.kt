package com.github.donghune.companyz.work.extension

import com.github.donghune.companyz.work.model.PartTimeJob
import com.github.donghune.companyz.work.model.PartTimeWorkRepository
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject

private val partTimeWorkRepository by inject<PartTimeWorkRepository>(PartTimeWorkRepository::class.java)

val Player.partTimeJob
    get() = partTimeWorkRepository.getPartTimeJobInProgress(this)

fun PartTimeJob.complete(player: Player) {
    partTimeWorkRepository.complete(player, index)
}