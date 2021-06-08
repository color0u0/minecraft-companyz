package com.github.donghune.companyz.work.inventory

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.work.extension.save
import com.github.donghune.companyz.work.model.Work
import com.github.donghune.namulibrary.extension.sendInfoMessage
import net.kyori.adventure.inventory.Book
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEditBookEvent


class WorkMissionEditBook(
    val work: Work
) : Listener {

    private val author = work.name + "e"

    fun open(player: Player) {
        Bukkit.getPluginManager().registerEvents(this, plugin)
        Book.builder()
            .addPage(Component.text(work.mission.textContent))
            .author(Component.text(author))
            .also { player.openBook(it) }
    }

    @EventHandler
    fun onPlayerEditBookEvent(event: PlayerEditBookEvent) {
        val player = event.player
        val book = event.previousBookMeta

        if (book.author != author) {
            return
        }

        work.mission.textContent = book.page(0).toString()
        work.save()
        player.sendInfoMessage("업무의 내용을 수정하였습니다.")

        PlayerEditBookEvent.getHandlerList().unregister(this)
    }

}