package com.github.donghune.companyz.util.command

import com.github.donghune.companyz.util.minecraft.ItemStackFactory
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.*
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.entity.TropicalFish
import org.bukkit.inventory.ItemFlag
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

class ItemCommand : Command() {
    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("item") {
                then("DisplayName") {
                    then("value" to string()) {
                        executes {
                            val player = it.sender as Player
                            val value = it.parseArgument<String>("value")

                            val itemStack = player.inventory.itemInMainHand
                            ItemStackFactory(itemStack).setDisplayName(value)
                            player.sendInfoMessage("아이템의 이름을 ${value}로 변경하였습니다.")
                        }
                    }
                }
                then("Type") {
                    then("value" to enum(Material.values())) {
                        executes {
                            val player = it.sender as Player
                            val value = it.parseArgument<Material>("value")

                            val itemStack = player.inventory.itemInMainHand
                            ItemStackFactory(itemStack).setType(value)
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

                                val itemStack = player.inventory.itemInMainHand
                                ItemStackFactory(itemStack).setAmount(value)
                                player.sendInfoMessage("아이템의 수량을 ${itemStack.amount}로 변경하였습니다.")
                            }
                        }
                    }
                    then("set") {
                        then("value" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<Int>("value")

                                val itemStack = player.inventory.itemInMainHand
                                ItemStackFactory(itemStack).addAmount(value)
                                player.sendInfoMessage("아이템의 수량을 ${itemStack.amount}로 변경하였습니다.")
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

                                val itemStack = player.inventory.itemInMainHand
                                ItemStackFactory(itemStack).addLore(value)
                                player.sendInfoMessage("아이템의 로어에 ${value}를 추가하였습니다.")
                            }
                        }
                    }
                    then("remove") {
                        then("index" to integer()) {
                            executes {
                                val player = it.sender as Player
                                val index = it.parseArgument<Int>("index")

                                val itemStack = player.inventory.itemInMainHand
                                ItemStackFactory(itemStack).removeLore(index)
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

                                    val itemStack = player.inventory.itemInMainHand
                                    ItemStackFactory(itemStack).editLore(index, value)
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

                                    val itemStack = player.inventory.itemInMainHand
                                    ItemStackFactory(itemStack).addUnsafeEnchantment(
                                        Enchantment.getByName(value)!!,
                                        level
                                    )
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

                                val itemStack = player.inventory.itemInMainHand
                                ItemStackFactory(itemStack).removeEnchantment(Enchantment.getByName(value)!!)
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

                                val itemStack = player.inventory.itemInMainHand
                                ItemStackFactory(itemStack).addItemFlags(value)
                                player.sendInfoMessage("아이템에 $value Flag 를 추가하였습니다.")
                            }
                        }
                    }
                    then("remove") {
                        then("value" to enum(ItemFlag.values())) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<ItemFlag>("value")

                                val itemStack = player.inventory.itemInMainHand
                                ItemStackFactory(itemStack).removeItemFlags(value)
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

                            val itemStack = player.inventory.itemInMainHand
                            ItemStackFactory(itemStack).setUnbreakable(value)
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
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toBannerMeta { meta ->

                                }
                                player.sendInfoMessage("BannerMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("BlockDataMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toBlockDataMeta { meta ->

                                }
                                player.sendInfoMessage("BlockDataMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("BlockStateMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toBlockStateMeta { meta ->

                                }
                                player.sendInfoMessage("BlockStateMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("BookMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toBookMeta { meta ->

                                }
                                player.sendInfoMessage("BookMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("CompassMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toCompassMeta { meta ->

                                }
                                player.sendInfoMessage("CompassMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("CrossbowMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toCrossbowMeta { meta ->

                                }
                                player.sendInfoMessage("CrossbowMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("EnchantmentStorageMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toEnchantmentStorageMeta { meta ->

                                }
                                player.sendInfoMessage("EnchantmentStorageMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("FireworkEffectMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toFireworkEffectMeta { meta ->

                                }
                                player.sendInfoMessage("FireworkEffectMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("FireworkMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toFireworkMeta { meta ->

                                }
                                player.sendInfoMessage("FireworkMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("KnowledgeBookMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toKnowledgeBookMeta { meta ->

                                }
                                player.sendInfoMessage("KnowledgeBookMeta 의 it 을 수정하였습니다.")
                            }
                        }
                    }
                }
                then("LeatherArmorMeta") {
                    then("set") {
                        then("value" to string()) {
                            executes {
                                val player = it.sender as Player
                                val value = it.parseArgument<String>("value")
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toLeatherArmorMeta { meta ->

                                }
                                player.sendInfoMessage("LeatherArmorMeta 의 it 을 수정하였습니다.")
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
                                                        val itemStack = player.inventory.itemInMainHand

                                                        ItemStackFactory(itemStack).toPotionMeta { meta ->
                                                            meta.addCustomEffect(
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
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toSuspiciousStewMeta { meta ->
                                    meta.removeCustomEffect(type)
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
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toSkullMeta { meta ->
                                    meta.playerProfile = value.playerProfile
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
                                                        val itemStack = player.inventory.itemInMainHand

                                                        ItemStackFactory(itemStack).toSuspiciousStewMeta { meta ->
                                                            meta.addCustomEffect(
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
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toSuspiciousStewMeta { meta ->
                                    meta.removeCustomEffect(type)
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
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toTropicalFishBucketMeta { meta ->
                                    meta.bodyColor = value
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
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toTropicalFishBucketMeta { meta ->
                                    meta.pattern = value
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
                                val itemStack = player.inventory.itemInMainHand

                                ItemStackFactory(itemStack).toTropicalFishBucketMeta { meta ->
                                    meta.patternColor = value
                                }
                                player.sendInfoMessage("TropicalFishBucketMeta 의 patternColor 을 수정하였습니다.")
                            }
                        }
                    }
                }
            }
        }
}