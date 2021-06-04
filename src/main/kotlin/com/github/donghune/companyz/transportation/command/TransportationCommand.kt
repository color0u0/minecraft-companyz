package com.github.donghune.companyz.transportation.command

import com.github.donghune.companyz.transportation.model.TransitPoint
import com.github.donghune.companyz.transportation.model.TransitPointRepository
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.string
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject

class TransportationCommand : Command() {

    private val transitPointRepository by inject<TransitPointRepository>(TransitPointRepository::class.java)

    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("transportation") {
                then("point") {
                    then("add") {
                        then("name" to string()) {
                            executes {
                                val player = it.sender as Player
                                val name = it.parseArgument<String>("name")

                                if (transitPointRepository.get(name) != null) {
                                    player.sendInfoMessage("이미 존재하는 지점 이름입니다.")
                                    return@executes
                                }

                                val transitPoint = TransitPoint(name, name, listOf(), player.location)
                                transitPointRepository.create(name, transitPoint)
                                player.sendInfoMessage("지점을 생성하였습니다.")
                            }
                        }
                    }
                    then("remove") {
                        then("point" to transitPoint()) {
                            executes {
                                val player = it.sender as Player
                                val transitPoint = it.parseArgument<TransitPoint>("point")

                                transitPointRepository.remove(transitPoint.name)
                                player.sendInfoMessage("지점을 삭제하였습니다.")
                            }
                        }
                    }
                    then("modify") {
                        then("display-name") {
                            then("point" to transitPoint()) {
                                executes {
                                    val player = it.sender as Player
                                    val transitPoint = it.parseArgument<TransitPoint>("point")

                                    transitPoint.display = it.rawArguments.joinToString(separator = " ")

                                    transitPointRepository.save(transitPoint.name)
                                    player.sendInfoMessage("지점을 정보를 수정하였습니다.")
                                }
                            }
                        }
                        then("lore") {
                            then("add") {
                                then("point" to transitPoint()) {
                                    then("message" to string()) {
                                        executes {
                                            val player = it.sender as Player
                                            val transitPoint = it.parseArgument<TransitPoint>("point")
                                            val message = it.parseArgument<String>("message")

                                            transitPoint.lore = transitPoint.lore.toMutableList().apply {
                                                add(message)
                                            }

                                            transitPointRepository.save(transitPoint.name)
                                            player.sendInfoMessage("지점을 정보를 수정하였습니다.")
                                        }
                                    }
                                }
                            }
                            then("remove") {
                                then("point" to transitPoint()) {
                                    then("index" to integer()) {
                                        executes {
                                            val player = it.sender as Player
                                            val transitPoint = it.parseArgument<TransitPoint>("point")
                                            val index = it.parseArgument<Int>("index")

                                            transitPoint.lore = transitPoint.lore.toMutableList().apply {
                                                removeAt(index)
                                            }

                                            transitPointRepository.save(transitPoint.name)
                                            player.sendInfoMessage("지점을 정보를 수정하였습니다.")
                                        }
                                    }
                                }
                            }
                            then("edit") {
                                then("point" to transitPoint()) {
                                    then("index" to integer()) {
                                        then("message" to string()) {
                                            executes {
                                                val player = it.sender as Player
                                                val transitPoint = it.parseArgument<TransitPoint>("point")
                                                val index = it.parseArgument<Int>("index")
                                                val message = it.parseArgument<String>("message")

                                                transitPoint.lore = transitPoint.lore.toMutableList().apply {
                                                    set(index, message)
                                                }

                                                transitPointRepository.save(transitPoint.name)
                                                player.sendInfoMessage("지점을 정보를 수정하였습니다.")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        then("location") {
                            then("point" to transitPoint()) {
                                executes {
                                    val player = it.sender as Player
                                    val transitPoint = it.parseArgument<TransitPoint>("point")

                                    transitPointRepository.save(transitPoint.name)
                                    player.sendInfoMessage("지점을 정보를 수정하였습니다.")
                                }
                            }
                        }
                    }
                }
            }
        }
}