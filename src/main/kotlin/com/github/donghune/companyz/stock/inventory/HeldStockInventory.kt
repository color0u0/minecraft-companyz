package com.github.donghune.companyz.stock.inventory

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.stock.extension.stock
import com.github.donghune.companyz.stock.model.HeldStock
import com.github.donghune.companyz.stock.model.Stock
import com.github.donghune.companyz.stock.model.StockRepository
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.nms.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.koin.java.KoinJavaComponent.inject

class HeldStockInventory(private val player: Player) : GUI(plugin, 9, "보유 주식 목록") {

    private val stockRepository: StockRepository by inject(StockRepository::class.java)

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun onPlayerInventoryClick(event: InventoryClickEvent) {

    }

    override suspend fun setContent() {
        player.stock.stocks.values
            .filter {
                it.amount != 0
            }
            .forEachIndexed { index, heldStock ->
                val stock = stockRepository.get(heldStock.name) ?: return@forEachIndexed

                setItem(index, stock.toHeldItemStack(heldStock)) {
                    it.isCancelled = true
                }
            }
    }

    private fun Stock.toHeldItemStack(heldStock: HeldStock): ItemStack {
        return ItemBuilder()
            .setMaterial(Material.BOOK)
            .setDisplay("&f$name")
            .setLore(
                listOf(
                    "&f평균단가 : &6${heldStock.buyPrice.toMoneyFormat()} &f딤화",
                    "&f보유개수 : &6${heldStock.amount.toMoneyFormat()}&f주",
                    "&f",
                    "&f전일가격 : &6${openingPrice.toMoneyFormat()} &f딤화",
                    "&f현재가격 : &6${tradePrice.toMoneyFormat()} &f딤화",
                    "&f",
                    "&f",
                )
            )
            .build()
            .addNBTTagCompound(this@toHeldItemStack)
    }
}