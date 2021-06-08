package com.github.donghune.companyz.shop.command

import com.github.donghune.companyz.shop.extension.addStuff
import com.github.donghune.companyz.shop.inventory.ShopInventory
import com.github.donghune.companyz.shop.inventory.StuffDeleteInventory
import com.github.donghune.companyz.shop.model.Shop
import com.github.donghune.companyz.shop.model.ShopRepository
import com.github.donghune.companyz.shop.model.ShopStuff
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.string
import com.github.shynixn.mccoroutine.launch
import org.bukkit.Material
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject


class ShopCommand : Command() {

    private val shopRepository by inject<ShopRepository>(ShopRepository::class.java)

    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("shop") {
                then("open") {
                    then("shop" to ShopArgument) {
                        executes {
                            val player = it.sender as Player
                            val shop = it.parseArgument<Shop>("shop")

                            ShopInventory(shop).open(player)
                        }
                    }
                }
                then("create") {
                    then("shopName" to string()) {
                        executes {
                            val player = it.sender as Player
                            val shopName = it.parseArgument<String>("shopName")

                            if (shopRepository.get(shopName) != null) {
                                player.sendErrorMessage("이미 존재하는 상점 이름입니다.")
                                return@executes
                            }

                            shopRepository.createDefaultData(shopName)
                            player.sendInfoMessage("상점을 생성하였습니다.")
                        }
                    }
                }
                then("delete") {
                    then("shop" to ShopArgument) {
                        executes {
                            val player = it.sender as Player
                            val shop = it.parseArgument<Shop>("shop")

                            shopRepository.remove(shop.name)
                            player.sendErrorMessage("상점을 삭제하였습니다.")
                        }
                    }
                }
                then("items") {
                    then("shop" to ShopArgument) {
                        then("add") {
                            then("buyPrice" to integer()) {
                                then("sellPrice" to integer()) {
                                    executes {
                                        val player = it.sender as Player
                                        val shop = it.parseArgument<Shop>("shop")
                                        val buyPrice = it.parseArgument<Int>("buyPrice")
                                        val sellPrice = it.parseArgument<Int>("sellPrice")
                                        val itemStack = player.inventory.itemInMainHand

                                        if (itemStack.type == Material.AIR) {
                                            player.sendErrorMessage("손에 든 아이템이 없습니다.")
                                            return@executes
                                        }

                                        shop.addStuff(ShopStuff(itemStack, buyPrice, sellPrice))
                                        player.sendInfoMessage("상점에 아이템을 추가하였습니다.")
                                    }
                                }
                            }
                        }
                        then("remove") {
                            executes {
                                val player = it.sender as Player
                                val shop = it.parseArgument<Shop>("shop")

                                com.github.donghune.companyz.plugin.launch {
                                    StuffDeleteInventory(shop).open(player)
                                }
                            }
                        }
                    }
                }
            }
        }
}