package com.github.donghune.companyz.work.model

import com.github.donghune.companyz.money.extension.money
import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.work.event.AcceptPartTimeJobEvent
import com.github.donghune.companyz.work.event.CompletePartTimeJobEvent
import com.github.donghune.companyz.work.event.ReAllocatePartTimeJobEvent
import com.github.donghune.companyz.work.extension.partTimeJob
import com.github.donghune.companyz.work.extension.toPartTimeJob
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.extension.toMoneyFormat
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject

class PartTimeWorkRepository {

    private var partTimeJobs = arrayOfNulls<PartTimeJob>(5)
    private val workRepository by inject<WorkRepository>(WorkRepository::class.java)

    fun getPartTimeJobs(): List<PartTimeJob?> {
        return partTimeJobs.toList()
    }

    fun getPartTimeJobInProgress(player: Player): PartTimeJob? {
        return partTimeJobs.filterNotNull()
            .filter { it.acceptedPlayer != null }
            .find { it.acceptedPlayer?.uniqueId == player.uniqueId }
    }

    fun allocations(): List<PartTimeJob> {
        return workRepository.getList()
            .shuffled()
            .subList(0, 5)
            .mapIndexed { index, work -> work.toPartTimeJob(index) }
            .also { partTimeJobs = it.toTypedArray() }
    }

    fun allocation(index: Int): PartTimeJob {
        return workRepository.getList()
            .shuffled()
            .first()
            .toPartTimeJob(index)
            .also { partTimeJobs[index] = it }
    }

    fun accept(player: Player, partTimeJob: PartTimeJob) {
        accept(player, partTimeJob.index)
    }

    fun accept(player: Player, index: Int) {

        val partTimeJob = partTimeJobs[index] ?: return

        if (partTimeJob.state != WorkState.PENDING) {
            player.sendErrorMessage("??????????????? ????????? ??????????????? ?????????.")
            return
        }

        if (player.partTimeJob != null) {
            player.sendErrorMessage("?????? ???????????? ?????????????????? ???????????????.")
            return
        }

        partTimeJob.acceptedPlayer = player
        partTimeJob.state = WorkState.ACCEPTED
        player.sendInfoMessage("?????????????????? ?????????????????????.")
        Bukkit.getPluginManager().callEvent(AcceptPartTimeJobEvent(player))
        return
    }

    fun complete(player: Player, index: Int, achievementRate: Double) {
        if (player.partTimeJob == null) {
            player.sendErrorMessage("???????????? ?????????????????? ???????????? ????????????.")
            return
        }

        val partTimeJob = partTimeJobs[index] ?: return
        partTimeJob.acceptedPlayer = player
        partTimeJob.state = WorkState.COMPLETE

        player.money += (partTimeJob.work.reward.money * achievementRate).toInt()

        player.sendInfoMessage("?????????????????? ?????????????????????.")
        player.sendInfoMessage("????????? ???????????????.")
        player.sendInfoMessage("????????? : ${(partTimeJob.work.reward.money * achievementRate).toInt().toMoneyFormat()}")
        player.sendInfoMessage("????????? : ${(partTimeJob.work.reward.money * achievementRate).toInt().toMoneyFormat()}")
        Bukkit.getPluginManager().callEvent(CompletePartTimeJobEvent(player))

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            val allocatedPartTimeJob = allocation(index)
            Bukkit.getPluginManager().callEvent(ReAllocatePartTimeJobEvent(allocatedPartTimeJob))
        }, ALLOCATE_TIME_TICK)
    }

    companion object {
        const val ALLOCATE_TIME_TICK = 60 * 10 * 20L
        const val ALLOCATE_TIME = 60 * 10 * 20L
    }

}