package com.github.donghune.companyz.stock.command

import com.github.donghune.companyz.stock.extension.delisting
import com.github.donghune.companyz.stock.inventory.HeldStockInventory
import com.github.donghune.companyz.stock.inventory.StockMarketInventory
import com.github.donghune.companyz.stock.model.Stock
import com.github.donghune.companyz.stock.model.StockRepository
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.player
import com.github.monun.kommand.argument.string
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent

class StockCommand : Command() {

    private val stockRepository by KoinJavaComponent.inject<StockRepository>(StockRepository::class.java)

    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("stock") {
                then("market") {
                    executes {
                        val player = it.sender as Player
                        StockMarketInventory().open(player)
                    }
                }
                then("held") {
                    executes {
                        val player = it.sender as Player
                        HeldStockInventory(player).open(player)
                    }
                    then("player" to player()) {
                        executes {
                            val player = it.sender as Player
                            val target = it.parseArgument<Player>("player")
                            HeldStockInventory(target).open(player)
                        }
                    }
                }
                then("debug") {
                    then("create") {
                        then("name" to string()) {
                            executes {
                                val player = it.sender as Player
                                val stockName = it.parseArgument<String>("name")

                                if (stockRepository.get(stockName) != null) {
                                    player.sendErrorMessage("?????? ???????????? ?????? ???????????????.")
                                    return@executes
                                }

                                stockRepository.createDefaultData(stockName)
                                player.sendInfoMessage("$stockName ????????? ?????????????????????.")
                            }
                        }
                    }
                    then("delete") {
                        then("stock" to StockArgument) {
                            executes {
                                val player = it.sender as Player
                                val stock = it.parseArgument<Stock>("stock")
                                stock.delisting()
                                player.sendInfoMessage("?????? ????????? ?????????????????????.")
                                player.sendInfoMessage("?????????????????? ???????????? ????????? ?????? ?????? ?????????.")
                            }
                        }
                    }
                    then("fluctuatePrice") {
                        then("min" to integer()) {
                            then("max" to integer()) {
                                executes {
                                    val min = it.parseArgument<Int>("min")
                                    val max = it.parseArgument<Int>("max")

                                    stockRepository.getList()
                                        .forEach { stock ->
                                            stock.fluctuatePrice(min, max)
                                        }

                                    Bukkit.getOnlinePlayers()
                                        .forEach { onlinePlayer ->
                                            onlinePlayer.sendInfoMessage("????????? ?????????????????????.")
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
}