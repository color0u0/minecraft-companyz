package com.github.donghune.companyz.shop.extension

import com.github.donghune.companyz.money.extension.money
import com.github.donghune.companyz.shop.model.ShopStuff
import com.github.donghune.namulibrary.extension.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun ShopStuff.buy(player: Player, buyAmount: Int) {
    if (player.money < buyPrice * buyAmount) {
        player.sendErrorMessage("보유금액이 부족합니다.")
        return
    }

    if (buyPrice == -1) {
        player.sendErrorMessage("구매를 할 수 없는 아이템 입니다.")
        return
    }

    val buyItem = itemStack.clone().apply { amount = buyAmount }
    val totalPrice = buyPrice * buyAmount
    player.money -= totalPrice
    player.inventory.addItem(buyItem)
    player.sendInfoMessage("아이템을 구매하였습니다. [-${totalPrice.toMoneyFormat()}]")
}

fun ShopStuff.sell(player: Player, sellItemStack: ItemStack) {

    if (sellPrice == -1) {
        player.sendErrorMessage("판매를 할 수 없는 아이템 입니다.")
        return
    }

    val totalPrice = sellPrice * sellItemStack.amount

    player.money += totalPrice
    sellItemStack.apply { amount = 0 }
    player.sendInfoMessage("아이템을 판매 하였습니다. [+${totalPrice.toMoneyFormat()}]")
}