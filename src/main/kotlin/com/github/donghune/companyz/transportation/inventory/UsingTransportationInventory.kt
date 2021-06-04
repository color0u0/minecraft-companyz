package com.github.donghune.companyz.transportation.inventory

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.transportation.extension.calculatePrice
import com.github.donghune.companyz.transportation.extension.use
import com.github.donghune.companyz.transportation.model.TransitPoint
import com.github.donghune.companyz.transportation.model.TransitPointRepository
import com.github.donghune.companyz.transportation.model.TransitPointRepository.Companion.METER_BY_PRICE
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.nms.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.koin.java.KoinJavaComponent.inject

class UsingTransportationInventory : GUI(plugin, 27, "이동하실 곳을 선택해주세요!") {

    private val repository by inject<TransitPointRepository>(TransitPointRepository::class.java)

    private lateinit var player: Player

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {

    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
        player = event.player as Player
    }

    override suspend fun setContent() {
        repository.getList()
            .forEachIndexed { index, transitPoint ->
                setItem(index, transitPoint.toItemStack(transitPoint.calculatePrice(player.location))) {
                    val player: Player = it.whoClicked as Player
                    it.isCancelled = true
                    transitPoint.use(player)
                }
            }
    }

    private fun TransitPoint.toItemStack(price: Int): ItemStack {
        return ItemBuilder()
            .setMaterial(Material.BOOK)
            .setDisplay(display)
            .setLore(lore.toMutableList().apply {
                add("&f")
                add("&f이용금액 : &6${price.toMoneyFormat()}")
                add("&f")
                add("&f현재 위치와 이동하려는 위치의 거리 비례 금액으로 계산됩니다!")
            })
            .build()
            .addNBTTagCompound(this@toItemStack)
    }

}