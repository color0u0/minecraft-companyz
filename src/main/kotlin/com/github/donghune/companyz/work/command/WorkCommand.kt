package com.github.donghune.companyz.work.command

import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.companyz.work.extension.save
import com.github.donghune.companyz.work.inventory.JobPostInventory
import com.github.donghune.companyz.work.inventory.WorkMissionEditInventory
import com.github.donghune.companyz.work.model.PartTimeWorkRepository
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
import org.koin.java.KoinJavaComponent.inject

class WorkCommand : Command() {
    private val workRepository by inject<WorkRepository>(WorkRepository::class.java)
    private val partTimeWorkRepository by inject<PartTimeWorkRepository>(PartTimeWorkRepository::class.java)

    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("work") {
                then("debug") {
                    then("title") {
                        executes {
                            val player = it.sender as Player

                            player.sendTitle("안녕", "화면에 출력되는 단어를 채팅창에 입력하세요 2.0s", 0, 60, 0)
                        }
                    }
                    then("allocation") {
                        executes {
                            val player = it.sender as Player

                            partTimeWorkRepository.allocations()
                            player.sendInfoMessage("아르바이트를 갱신하였습니다.")
                        }
                    }
                }
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
                            player.sendInfoMessage("해당 업무를 생성하였습니다.")
                        }
                    }
                }
                then("remove") {
                    then("work" to WorkArgument) {
                        executes {
                            val player = it.sender as Player
                            val work = it.parseArgument<Work>("work")

                            workRepository.remove(work.name)
                            player.sendInfoMessage("해당 업무를 삭제하였습니다.")
                        }
                    }
                }
                then("modify") {
                    then("work" to WorkArgument) {
                        then("display") {
                            then("value" to string()) {
                                executes {
                                    val player = it.sender as Player
                                    val work = it.parseArgument<Work>("work")
                                    val value = it.parseArgument<String>("value")

                                    work.display = value.replace("_", " ")
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

                                    work.description = value.replace("_", " ")
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

                                        work.mission.toWhom = value.replace("_", " ")
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
                                then("value" to string()) {
                                    executes {
                                        val player = it.sender as Player
                                        val work = it.parseArgument<Work>("work")
                                        val value = it.parseArgument<String>("value")

                                        work.mission.textContent = value.replace("_", " ")
                                        work.save()
                                        player.sendInfoMessage("업무의 내용을 수정하였습니다.")
                                    }
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