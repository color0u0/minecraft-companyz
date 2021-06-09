package com.github.donghune.companyz.work.inventory

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.work.extension.save
import com.github.donghune.companyz.work.model.Work
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class WorkMissionEditInventory(
    val work: Work
) : GUI(plugin, 9, "조달 업무에 목표 아이템을 등록해주세요") {

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
        event.inventory.storageContents
            .toList()
            .filter { it.type != Material.AIR }
            .also { work.mission.itemStacks = it }
            .also { work.save() }
        (event.player as Player).sendInfoMessage("업무의 내용을 수정하였습니다.")
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {

    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {

    }

    override suspend fun setContent() {
        inventory.addItem(*work.mission.itemStacks.toTypedArray())
    }
}