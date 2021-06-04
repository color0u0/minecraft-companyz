package com.github.donghune.companyz.stock.extension

import com.github.donghune.companyz.money.extension.money
import com.github.donghune.companyz.stock.model.HeldStock
import com.github.donghune.companyz.stock.model.PlayerStock
import com.github.donghune.companyz.stock.model.PlayerStockRepository
import com.github.donghune.companyz.stock.model.Stock
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject
import java.lang.Exception

private val playerStockRepository by inject<PlayerStockRepository>(PlayerStockRepository::class.java)

val Player.stock: PlayerStock
    get() {
        return playerStockRepository.getSafety(uniqueId.toString())
    }

val OfflinePlayer.stock: PlayerStock
    get() {
        return playerStockRepository.getSafety(uniqueId.toString())
    }

fun PlayerStock.buy(stock: Stock, amount: Int) {
    val player = Bukkit.getPlayer(uuid) ?: return

    if (player.money < stock.tradePrice * amount) {
        player.sendErrorMessage("보유중인 금액이 부족합니다.")
        return
    }

    if (stocks[stock.name] == null) {
        stocks[stock.name] = HeldStock(stock.name, 0, 0)
    }

    val heldStock = stocks[stock.name] ?: throw Exception("what the??")

    val beforePrice = heldStock.buyPrice * heldStock.amount
    val nowPrice = stock.tradePrice * amount

    heldStock.buyPrice = (beforePrice + nowPrice) / (heldStock.amount + amount)
    heldStock.amount += amount

    player.money -= stock.tradePrice * amount
    player.sendInfoMessage("${stock.name} 주식을 &6${amount}&f주 매수 하였습니다.")
    playerStockRepository.save(uuid.toString())
}

fun PlayerStock.sell(stock: Stock, amount: Int) {
    val player = Bukkit.getPlayer(uuid) ?: return

    if ((stocks[stock.name]?.amount ?: 0) < amount) {
        player.sendErrorMessage("해당 주식을 충분히 보유하고 있지 않습니다.")
        return
    }

    if (stocks[stock.name] == null) {
        stocks[stock.name] = HeldStock(stock.name, 0, 0)
    }

    val heldStock = stocks[stock.name] ?: throw Exception("what the??")

    heldStock.amount -= amount

    player.money += stock.tradePrice * amount
    player.sendInfoMessage("${stock.name} 주식을 &6${amount}&f주 매도 하였습니다.")
    playerStockRepository.save(uuid.toString())
}