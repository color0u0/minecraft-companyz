package com.github.donghune.companyz.stock.inventory

import com.github.donghune.companyz.money.extension.money
import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.stock.extension.buy
import com.github.donghune.companyz.stock.extension.sell
import com.github.donghune.companyz.stock.extension.stock
import com.github.donghune.companyz.stock.model.Stock
import com.github.donghune.companyz.stock.model.StockRepository
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.inventory.GUI
import com.github.donghune.namulibrary.nms.addNBTTagCompound
import com.github.donghune.namulibrary.nms.getNBTTagCompound
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.koin.java.KoinJavaComponent.inject

object StockMarketInventory : GUI(plugin, 9, "주식 거래장") {

    private val stockRepository: StockRepository by inject(StockRepository::class.java)

    override suspend fun onInventoryClose(event: InventoryCloseEvent) {
    }

    override suspend fun onInventoryOpen(event: InventoryOpenEvent) {
    }

    override suspend fun setContent() {
        stockRepository.getList()
            .map { it.toMarketItemStack() }
            .forEachIndexed { index: Int, itemStack: ItemStack ->
                setItem(index, itemStack) {
                    it.isCancelled = true

                    val player = it.whoClicked as Player
                    val clickType = it.click

                    val item = it.currentItem ?: return@setItem
                    val stock = item.getNBTTagCompound(Stock::class.java) ?: return@setItem

                    when (clickType) {
                        ClickType.LEFT -> player.stock.buy(stock, 1)
                        ClickType.SHIFT_LEFT -> player.stock.buy(stock, player.money / stock.tradePrice)
                        ClickType.RIGHT -> player.stock.sell(stock, 1)
                        ClickType.SHIFT_RIGHT -> player.stock.sell(stock, player.stock.stocks[stock.name]?.amount ?: 1)
                        else -> return@setItem
                    }
                }
            }
    }

    private fun Stock.toMarketItemStack(): ItemStack {
        return ItemBuilder()
            .setMaterial(Material.BOOK)
            .setDisplay("&f$name")
            .setLore(
                listOf(
                    "&f전일가격 : &6${openingPrice.toMoneyFormat()} 딤화",
                    "&f현재가격 : &6${tradePrice.toMoneyFormat()} 딤화",
                    "&f",
                    "&f좌클릭 : &61&f주 매수",
                    "&f우클릭 : &61&f주 매도",
                    "&f쉬프트를 같이 누를 시 &6전량&f 매도 및 매수가 이루어집니다."
                )
            )
            .build()
            .addNBTTagCompound(this)
    }
}