package com.github.donghune.companyz.money.listener

import com.github.donghune.companyz.money.extension.money
import com.github.donghune.companyz.money.model.CashItem
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.nms.getNBTTagCompound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class MoneyListener : Listener {

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerInteractEvent) {
        val player = event.player
        val itemStack = player.inventory.itemInMainHand
        val cashItem = itemStack.getNBTTagCompound(CashItem::class.java) ?: return

        itemStack.amount--
        player.money += cashItem.value
        player.sendInfoMessage("수표를 사용하여 ${cashItem.value.toMoneyFormat()} 만큼 획득하였습니다.")
    }

}