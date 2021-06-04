package com.github.donghune.companyz.stock.extension

import com.github.donghune.companyz.stock.model.Stock
import com.github.donghune.companyz.stock.model.StockRepository
import org.bukkit.Bukkit
import org.koin.java.KoinJavaComponent.inject

private val repository by inject<StockRepository>(StockRepository::class.java)

fun Stock.save() {
    repository.save(name)
}

fun Stock.delisting() {
    repository.remove(name)
    Bukkit.getOfflinePlayers().forEach { offlinePlayer ->
        offlinePlayer.stock.stocks.remove(name)
    }
}