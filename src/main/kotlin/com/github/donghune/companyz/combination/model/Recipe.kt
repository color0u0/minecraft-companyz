package com.github.donghune.companyz.combination.model

import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.nms.addNBTTagCompound
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack
import java.io.Serializable

@SerializableAs("Recipe")
data class Recipe(
    val id: String,
    val display: String,
    val description: List<String>,
    val materials: List<ItemStack>,
    val resultItem: ItemStack
) : ConfigurationSerializable {

    fun toItemStack(): ItemStack {
        return ItemBuilder()
            .setMaterial(Material.PAPER)
            .setDisplay(display)
            .setLore(
                listOf(
                    "",
                    *description.toTypedArray(),
                    "",
                    *toMaterialLore().toTypedArray()
                )
            )
            .build()
    }

    fun toRecipeBook(): ItemStack {
        return toItemStack().addNBTTagCompound(this)
    }

    private fun toMaterialLore(): List<String> {
        val itemMap = mutableMapOf<Pair<String, Material>, Int>()
        materials.forEach { itemStack: ItemStack ->
            val key = itemStack.displayName().toString() to itemStack.type
            itemMap[key] = (itemMap[key] ?: 0) + itemStack.amount
        }
        return itemMap
            .map { (if (it.key.first.isEmpty()) it.key.second.toString() else it.key.first) to it.value }
            .map { pair: Pair<String, Int> -> "${pair.first}x${pair.second}" }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "display" to display,
            "description" to description,
            "materials" to materials,
            "resultItem" to resultItem,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Recipe {
            return Recipe(
                data["id"] as String,
                data["display"] as String,
                data["description"] as List<String>,
                data["materials"] as List<ItemStack>,
                data["resultItem"] as ItemStack,
            )
        }
    }
}