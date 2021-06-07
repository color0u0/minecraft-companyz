package com.github.donghune.companyz.shop.model

import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import com.github.donghune.namulibrary.extension.toMoneyFormat
import com.github.donghune.namulibrary.nms.addNBTTagCompound
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack

@SerializableAs("ShopStuff")
data class ShopStuff(
    val itemStack: ItemStack,
    val buyPrice: Int,
    val sellPrice: Int,
) : ConfigurationSerializable {

    fun toStuffItemStack(): ItemStack {
        return itemStack.clone().apply {
            (lore() ?: mutableListOf<Component>()).apply {
                add(Component.text(""))
                add(Component.text("&f구매가격 : &6${if (buyPrice < 0) "&c구매불가" else buyPrice.toMoneyFormat()}".replaceChatColorCode()))
                add(Component.text("&f판매가격 : &6${if (sellPrice < 0) "&c판매불가" else sellPrice.toMoneyFormat()}".replaceChatColorCode()))
            }.also { lore(it) }
        }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "itemStack" to itemStack,
            "buyPrice" to buyPrice,
            "sellPrice" to sellPrice,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): ShopStuff {
            return ShopStuff(
                data["itemStack"] as ItemStack,
                data["buyPrice"] as Int,
                data["sellPrice"] as Int,
            )
        }
    }
}