package com.github.donghune.companyz.work.command

import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.companyz.work.extension.save
import com.github.donghune.companyz.work.inventory.JobPostInventory
import com.github.donghune.companyz.work.inventory.WorkMissionEditBook
import com.github.donghune.companyz.work.inventory.WorkMissionEditInventory
import com.github.donghune.companyz.work.model.Work
import com.github.donghune.companyz.work.model.WorkRepository
import com.github.donghune.companyz.work.model.WorkType
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.enum
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.string
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent

class WorkCommand : Command() {
    private val workRepository by KoinJavaComponent.inject<WorkRepository>(WorkRepository::class.java)

    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("work") {
                then("board") {
                    executes {
                        val player = it.sender as Player
                        JobPostInventory().open(player)
                    }
                }
                then("add") {
                    then("work-key" to string()) {
                        executes {
                            val player = it.sender as Player
                            val workKey = it.parseArgument<String>("work-key")

                            if (workRepository.get(workKey) != null) {
                                player.sendErrorMessage("이미 존재하는 업무 입니다.")
                                return@executes
                            }

                            workRepository.createDefaultData(workKey)
                        }
                    }
                }
                then("remove") {
                    then("work" to work()) {
                        executes {
                            val player = it.sender as Player
                            val work = it.parseArgument<Work>("work")

                            workRepository.remove(work.name)
                            player.sendInfoMessage("해당 업무를 삭제하였습니다.")
                        }
                    }
                }
                then("modify") {
                    then("work" to work()) {
                        then("display") {
                            then("value" to string()) {
                                executes {
                                    val player = it.sender as Player
                                    val work = it.parseArgument<Work>("work")
                                    val value = it.parseArgument<String>("value")

                                    work.display = value
                                    work.save()
                                    player.sendInfoMessage("업무의 내용을 수정하였습니다.")
                                }
                            }
                        }
                        then("description") {
                            then("value" to string()) {
                                executes {
                                    val player = it.sender as Player
                                    val work = it.parseArgument<Work>("work")
                                    val value = it.parseArgument<String>("value")

                                    work.description = value
                                    work.save()
                                    player.sendInfoMessage("업무의 내용을 수정하였습니다.")
                                }
                            }
                        }
                        then("mission") {
                            then("toWhom") {
                                then("value" to string()) {
                                    executes {
                                        val player = it.sender as Player
                                        val work = it.parseArgument<Work>("work")
                                        val value = it.parseArgument<String>("value")

                                        work.mission.toWhom = value
                                        work.save()
                                        player.sendInfoMessage("업무의 내용을 수정하였습니다.")
                                    }
                                }
                            }
                            then("itemStackList") {
                                executes {
                                    val player = it.sender as Player
                                    val work = it.parseArgument<Work>("work")

                                    WorkMissionEditInventory(work).open(player)
                                }
                            }
                            then("textContent") {
                                executes {
                                    val player = it.sender as Player
                                    val work = it.parseArgument<Work>("work")

                                    WorkMissionEditBook(work).open(player)
                                }
                            }
                        }
                        then("workType") {
                            then("value" to enum(WorkType.values())) {
                                executes {
                                    val player = it.sender as Player
                                    val work = it.parseArgument<Work>("work")
                                    val value = it.parseArgument<WorkType>("value")

                                    work.workType = value
                                    work.save()
                                    player.sendInfoMessage("업무의 내용을 수정하였습니다.")
                                }
                            }
                        }
                        then("reward") {
                            then("money") {
                                then("value" to integer()) {
                                    executes {
                                        val player = it.sender as Player
                                        val work = it.parseArgument<Work>("work")
                                        val value = it.parseArgument<Int>("value")

                                        work.reward.money = value
                                        work.save()
                                        player.sendInfoMessage("업무의 내용을 수정하였습니다.")
                                    }
                                }
                            }
                            then("reputation") {
                                then("value" to integer()) {
                                    executes {
                                        val player = it.sender as Player
                                        val work = it.parseArgument<Work>("work")
                                        val value = it.parseArgument<Int>("value")

                                        work.reward.reputation = value
                                        work.save()
                                        player.sendInfoMessage("업무의 내용을 수정하였습니다.")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
}