package com.github.donghune.companyz.work.listener

import com.github.donghune.companyz.work.event.AcceptPartTimeJobEvent
import com.github.donghune.companyz.work.extension.complete
import com.github.donghune.companyz.work.extension.partTimeJob
import com.github.donghune.companyz.work.model.WorkType
import com.github.donghune.namulibrary.extension.hasItems
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.takeItems
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEditBookEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class WorkListener : Listener {

    @EventHandler
    fun onPlayerInteractAtEntityEvent(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val entity = event.rightClicked
        val partTimeJob = player.partTimeJob ?: return
        val work = partTimeJob.work

        if (work.workType != WorkType.PROCUREMENT) {
            return
        }

        if (work.mission.toWhom != entity.name) {
            return
        }

        if (!player.inventory.hasItems(work.mission.itemStackList.toTypedArray())) {
            return
        }

        player.inventory.takeItems(work.mission.itemStackList.toTypedArray())
        partTimeJob.complete(player)
    }

    @EventHandler
    fun onPlayerEditBookEvent(event: PlayerEditBookEvent) {
        val player = event.player
        val partTimeJob = player.partTimeJob ?: return
        val work = partTimeJob.work

        if (work.workType != WorkType.ADMINISTRATIVE_AFFAIRS) {
            return
        }

        val book = event.previousBookMeta

        if (book.page(0).toString() != book.page(1).toString()) {
            player.sendErrorMessage("아르바이트 실패!")
            return
        }

        partTimeJob.complete(player)
    }

    @EventHandler
    fun onAcceptPartTimeJobEvent(event: AcceptPartTimeJobEvent) {
        val player = event.player
        val partTimeJob = player.partTimeJob ?: return
        val work = partTimeJob.work

        if (work.workType != WorkType.ADMINISTRATIVE_AFFAIRS) {
            return
        }

        Book.builder()
            .addPage(Component.text(work.mission.textContent))
            .addPage(Component.text(""))
            .build()
            .also { player.openBook(it) }
    }

}