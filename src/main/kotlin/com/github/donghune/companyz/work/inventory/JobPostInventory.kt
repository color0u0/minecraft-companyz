package com.github.donghune.companyz.work.inventory

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.work.model.PartTimeWorkRepository
import com.github.donghune.namulibrary.inventory.GUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.koin.java.KoinJavaComponent.inject

class JobPostInventory : GUI(plugin, 27, "구인 게시판") {

    private val partTimeWorkRepository by inject<PartTimeWorkRepository>(PartTimeWorkRepository::class.java)

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {
    }

    override suspend fun setContent() {
        partTimeWorkRepository.getPartTimeJobs().forEachIndexed { index, partTimeJob ->
            setItem(index, partTimeJob?.toItemStack() ?: ItemStack(Material.AIR)) {
                partTimeJob?: return@setItem
                it.isCancelled = true

                val player = it.whoClicked as Player
                partTimeWorkRepository.accept(player, partTimeJob.index)
                refreshContent()
            }
        }
    }
}