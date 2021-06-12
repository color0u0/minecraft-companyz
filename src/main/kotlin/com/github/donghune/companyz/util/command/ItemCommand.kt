package com.github.donghune.companyz.util.command

import com.github.donghune.companyz.util.minecraft.edit
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.*
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.entity.TropicalFish
import org.bukkit.inventory.ItemFlag
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class ItemCommand : Command() {
    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("item") {
                then("DisplayName") {
                    then("value" to string()) {
                        executes {
                            val player = it.sender as Player
                            val value = it.parseArgument<String>("value")

                            player.inventory.itemInMainHand.edit {
                                setDisplayName(value)
                            }

                            player.sendInfoMessage("아이템의 이름을 ${value}로 변경하였습니다.")
                        }
                    }
                }
                then("Type") {
                    then("value" to enum(Material.values())) {
                        executes {
                            val player = it.sender as Player
                            val value = it.parseArgument<Material>("value")

                            player.inventory.itemInMainHand.edit {
                                setType(value)
                            }

                            player.sendInfoMessage("아이템의 타입을 ${value}로 변경하였습니다.")
                        }
                    }
                }
                then("Amount") {
                    then("add") {
                        then("value" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<Int>("value")

                                player.inventory.itemInMainHand.edit {
                                    setAmount(value)
                                }

                                player.sendInfoMessage("아이템의 수량을 ${player.inventory.itemInMainHand.amount}로 변경하였습니다.")
                            }
                        }
                    }
                    then("set") {
                        then("value" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<Int>("value")

                                player.inventory.itemInMainHand.edit {
                                    addAmount(value)
                                }

                                player.sendInfoMessage("아이템의 수량을 ${player.inventory.itemInMainHand.amount}로 변경하였습니다.")
                            }
                        }
                    }
                }
                then("Lore") {
                    then("add") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")

                                player.inventory.itemInMainHand.edit {
                                    addLore(value)
                                }

                                player.sendInfoMessage("아이템의 로어에 ${value}를 추가하였습니다.")
                            }
                        }
                    }
                    then("remove") {
                        then("index" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val index = it.parseArgument<Int>("index")

                                player.inventory.itemInMainHand.edit {
                                    removeLore(index)
                                }

                                player.sendInfoMessage("아이템의 ${index}반째 로어를 삭제하였습니다.")
                            }
                        }
                    }
                    then("edit") {
                        then("index" to integer()) {
                            then("value" to string()) {
                                executes {
                                    val player = it.sender as Player
                                    val index = it.parseArgument<Int>("index")
                                    val value = it.parseArgument<String>("value")

                                    player.inventory.itemInMainHand.edit {
                                        editLore(index, value)
                                    }

                                    player.sendInfoMessage("아이템의 ${index}번째 로어를 ${value}로 변경하였습니다.")
                                }
                            }
                        }
                    }
                }
                then("Enchantment") {
                    then("add") {
                        then("value" to string(Enchantment.values().map { it.key.key })) {
                            then("level" to integer()) {
                                executes {
                                    val player = it.sender as Player
                                    val value = it.parseArgument<String>("value")
                                    val level = it.parseArgument<Int>("level")

                                    player.inventory.itemInMainHand.edit {
                                        addUnsafeEnchantment(
                                            Enchantment.values().find { it.key.key == value }!!,
                                            level
                                        )
                                    }

                                    player.sendInfoMessage("아이템에 Lv.$level $value 인첸트를 추가하였습니다.")
                                }
                            }
                        }
                    }
                    then("remove") {
                        then("value" to string(Enchantment.values().map { it.key.key })) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")

                                player.inventory.itemInMainHand.edit {
                                    removeEnchantment(
                                        Enchantment.getByName(value)!!
                                    )
                                }

                                player.sendInfoMessage("아이템의 $value 인첸트를 삭제하였습니다.")
                            }
                        }
                    }
                }
                then("ItemFlags") {
                    then("add") {
                        then("value" to enum(ItemFlag.values())) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<ItemFlag>("value")

                                player.inventory.itemInMainHand.edit {
                                    addItemFlags(value)
                                }

                                player.sendInfoMessage("아이템에 $value Flag 를 추가하였습니다.")
                            }
                        }
                    }
                    then("remove") {
                        then("value" to enum(ItemFlag.values())) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<ItemFlag>("value")

                                player.inventory.itemInMainHand.edit {
                                    removeItemFlags(value)
                                }

                                player.sendInfoMessage("아이템의 $value Flag 를 삭제하였습니다.")
                            }
                        }
                    }
                }
                then("Unbreakable") {
                    then("value" to bool()) {
                        executes {
                            val player = it.sender as Player
                            val value = it.parseArgument<Boolean>("value")

                            player.inventory.itemInMainHand.edit {
                                setUnbreakable(value)
                            }

                            player.sendInfoMessage("아이템의 부서짐을 ${if (value) "활성화" else "비활성화"} 하였습니다.")
                        }
                    }
                }
                then("BannerMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")

                                player.inventory.itemInMainHand.edit {
                                    BannerMeta {
                                    }
                                }

                                player.sendInfoMessage("BannerMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("EnchantmentStorageMeta") {
                    then("set") {
                        then("value" to string(Enchantment.values().map { it.key.key })) {
                            then("level" to integer()) {
                                executes {
                                    val player = it.sender as Player
                                    val value = it.parseArgument<String>("value")
                                    val level = it.parseArgument<Int>("level")

                                    player.inventory.itemInMainHand.edit {
                                        EnchantmentStorageMeta {
                                            addStoredEnchant(
                                                Enchantment.getByName(value)!!,
                                                level,
                                                false
                                            )
                                        }
                                    }

                                    player.sendInfoMessage("아이템에 Lv.$level $value 인첸트를 추가하였습니다.")
                                }
                            }
                        }
                    }
                }
                then("LeatherArmorMeta") {
                    then("set") {
                        then("red" to integer()) {
                            then("blue" to integer()) {
                                then("green" to integer()) {
                                    executes {
                                        val player = it.sender as Player
                                        val red = it.parseArgument<Int>("red")
                                        val blue = it.parseArgument<Int>("blue")
                                        val green = it.parseArgument<Int>("green")

                                        player.inventory.itemInMainHand.edit {
                                            LeatherArmorMeta {
                                                setColor(Color.fromRGB(red, green, blue))
                                            }
                                        }

                                        player.sendInfoMessage("LeatherArmorMeta 의 color 를 수정하였습니다.")
                                    }
                                }
                            }
                        }
                    }
                }
                then("PotionMeta") {
                    then("add") {
                        then("type" to string(PotionEffectType.values().map { it.name })) {
                            then("duration" to integer()) {
                                then("amplifier" to integer()) {
                                    then("ambient" to bool()) {
                                        then("particles" to bool()) {
                                            then("icon" to bool()) {
                                                then("value" to string()) {
                                                    executes {
                                                        val player = it.sender as Player
                                                        val type =
                                                            PotionEffectType.getByName(it.parseArgument("type"))!!
                                                        val duration = it.parseArgument<Int>("duration")
                                                        val amplifier = it.parseArgument<Int>("amplifier")
                                                        val ambient = it.parseArgument<Boolean>("ambient")
                                                        val particles = it.parseArgument<Boolean>("particles")
                                                        val icon = it.parseArgument<Boolean>("icon")

                                                        player.inventory.itemInMainHand.edit {
                                                            PotionMeta {
                                                                addCustomEffect(
                                                                    PotionEffect(
                                                                        type,
                                                                        duration,
                                                                        amplifier,
                                                                        ambient,
                                                                        particles,
                                                                        icon
                                                                    ),
                                                                    true
                                                                )
                                                            }
                                                        }

                                                        player.sendInfoMessage("SuspiciousStewMeta 의 customEffect 를 추가하였습니다.")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    then("remove") {
                        then("type" to string(PotionEffectType.values().map { it.name })) {
                            executes {
                                val player = it.sender as Player
                                val type = PotionEffectType.getByName(it.parseArgument("type"))!!

                                player.inventory.itemInMainHand.edit {
                                    SuspiciousStewMeta {
                                        removeCustomEffect(type)
                                    }
                                }

                                player.sendInfoMessage("SuspiciousStewMeta 의 customEffect 를 삭제하였습니다.")
                            }
                        }
                    }
                }
                then("SkullMeta") {
                    then("owner") {
                        then("value" to player()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<Player>("value")

                                player.inventory.itemInMainHand.edit {
                                    SkullMeta {
                                        playerProfile = value.playerProfile
                                    }
                                }

                                player.sendInfoMessage("SkullMeta 의 playerProfile 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("SuspiciousStewMeta") {
                    then("add") {
                        then("type" to string(PotionEffectType.values().map { it.name })) {
                            then("duration" to integer()) {
                                then("amplifier" to integer()) {
                                    then("ambient" to bool()) {
                                        then("particles" to bool()) {
                                            then("icon" to bool()) {
                                                then("value" to string()) {
                                                    executes {
                                                        val player = it.sender as Player
                                                        val type =
                                                            PotionEffectType.getByName(it.parseArgument("type"))!!
                                                        val duration = it.parseArgument<Int>("duration")
                                                        val amplifier = it.parseArgument<Int>("amplifier")
                                                        val ambient = it.parseArgument<Boolean>("ambient")
                                                        val particles = it.parseArgument<Boolean>("particles")
                                                        val icon = it.parseArgument<Boolean>("icon")

                                                        player.inventory.itemInMainHand.edit {
                                                            SuspiciousStewMeta {
                                                                addCustomEffect(
                                                                    PotionEffect(
                                                                        type,
                                                                        duration,
                                                                        amplifier,
                                                                        ambient,
                                                                        particles,
                                                                        icon
                                                                    ),
                                                                    true
                                                                )
                                                            }
                                                        }

                                                        player.sendInfoMessage("SuspiciousStewMeta 의 customEffect 를 추가하였습니다.")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    then("remove") {
                        then("type" to string(PotionEffectType.values().map { it.name })) {
                            executes {
                                val player = it.sender as Player
                                val type = PotionEffectType.getByName(it.parseArgument("type"))!!

                                player.inventory.itemInMainHand.edit {
                                    SuspiciousStewMeta {
                                        removeCustomEffect(type)
                                    }
                                }

                                player.sendInfoMessage("SuspiciousStewMeta 의 customEffect 를 삭제하였습니다.")
                            }
                        }
                    }
                }
                then("TropicalFishBucketMeta") {
                    then("bodyColor") {
                        then("value" to enum(DyeColor.values())) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<DyeColor>("value")

                                player.inventory.itemInMainHand.edit {
                                    TropicalFishBucketMeta {
                                        bodyColor = value
                                    }
                                }

                                player.sendInfoMessage("TropicalFishBucketMeta 의 bodyColor 을 수정하였습니다.")
                            }
                        }
                    }
                    then("pattern") {
                        then("value" to enum(TropicalFish.Pattern.values())) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<TropicalFish.Pattern>("value")

                                player.inventory.itemInMainHand.edit {
                                    TropicalFishBucketMeta {
                                        pattern = value
                                    }
                                }

                                player.sendInfoMessage("TropicalFishBucketMeta 의 pattern 을 수정하였습니다.")
                            }
                        }
                    }
                    then("patternColor") {
                        then("value" to enum(DyeColor.values())) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<DyeColor>("value")

                                player.inventory.itemInMainHand.edit {
                                    TropicalFishBucketMeta {
                                        patternColor = value
                                    }
                                }

                                player.sendInfoMessage("TropicalFishBucketMeta 의 patternColor 을 수정하였습니다.")
                            }
                        }
                    }
                }
            }
        }

}