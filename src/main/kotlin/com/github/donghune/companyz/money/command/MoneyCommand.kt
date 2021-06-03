package com.github.donghune.companyz.money.command

import com.github.donghune.companyz.money.extension.money
import com.github.donghune.companyz.money.model.CashItem
import com.github.donghune.companyz.money.model.PlayerMoneyRepository
import com.github.donghune.companyz.util.extension.isContentFull
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.player
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.koin.core.component.KoinComponent

class MoneyCommand : Command(), KoinComponent {

    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("money") {
                then("view") {
                    then("player" to player()) {
                        executes {
                            val player = it.sender as Player
                            val target = it.parseArgument<Player>("player")

                            if (!player.isOp) {
                                player.sendErrorMessage("관리자만 사용 가능한 명령어 입니다.")
                                return@executes
                            }

                            player.sendInfoMessage("${player.name}님의 보유금액은 ${target.money.toMoneyFormat()} 입니다.")
                        }
                    }
                    executes {
                        val player = it.sender as Player

                        player.sendInfoMessage("${player.name}님의 보유금액은 ${player.money.toMoneyFormat()} 입니다.")
                    }
                }
                then("check") {
                    then("amount" to integer()) {
                        executes {
                            val player = it.sender as Player
                            val amount = it.parseArgument<Int>("amount")

                            if (player.money < amount) {
                                player.sendErrorMessage("수표를 발행하기 위한 보유금액이 부족합니다.")
                                return@executes
                            }

                            if (player.inventory.isContentFull()) {
                                player.sendErrorMessage("인벤토리가 가득 차 수표를 발행 할 수 없습니다.")
                                return@executes
                            }

                            player.money -= amount
                            player.inventory.addItem(CashItem(amount).toItemStack())
                            player.sendInfoMessage("${amount.toMoneyFormat()} 금액의 수표를 발행하였습니다.")
                        }
                    }
                }
                then("give") {
                    then("player" to player()) {
                        then("amount" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val target = it.parseArgument<Player>("player")
                                val amount = it.parseArgument<Int>("amount")

                                if (!player.isOp) {
                                    player.sendErrorMessage("관리자만 사용 가능한 명령어 입니다.")
                                    return@executes
                                }

                                target.money += amount

                                player.sendInfoMessage("${target.name}님의 보유금액에서 ${amount.toMoneyFormat()} 만큼 지급하였습니다.")
                            }
                        }
                    }
                }
                then("take") {
                    then("player" to player()) {
                        then("amount" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val target = it.parseArgument<Player>("player")
                                val amount = it.parseArgument<Int>("amount")

                                if (!player.isOp) {
                                    player.sendErrorMessage("관리자만 사용 가능한 명령어 입니다.")
                                    return@executes
                                }

                                target.money -= amount

                                player.sendInfoMessage("${target.name}님의 보유금액에서 ${amount.toMoneyFormat()} 만큼 차감하였습니다.")
                            }
                        }
                    }
                }
                then("set") {
                    then("player" to player()) {
                        then("amount" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val target = it.parseArgument<Player>("player")
                                val amount = it.parseArgument<Int>("amount")

                                if (!player.isOp) {
                                    player.sendErrorMessage("관리자만 사용 가능한 명령어 입니다.")
                                    return@executes
                                }

                                target.money = amount

                                player.sendInfoMessage("${target.name}님의 보유금액을 ${amount.toMoneyFormat()} 으로 설정하였습니다.")
                            }
                        }
                    }
                }
                then("reset") {
                    then("player" to player()) {
                        executes {
                            val player = it.sender as Player
                            val target = it.parseArgument<Player>("player")

                            if (!player.isOp) {
                                player.sendErrorMessage("관리자만 사용 가능한 명령어 입니다.")
                                return@executes
                            }

                            target.money = PlayerMoneyRepository.DEFAULT_MONEY

                            player.sendInfoMessage("${target.name}님의 보유금액을 ${target.money.toMoneyFormat()} 으로 초기화 시켰습니다.")
                        }
                    }
                }
            }
        }
}