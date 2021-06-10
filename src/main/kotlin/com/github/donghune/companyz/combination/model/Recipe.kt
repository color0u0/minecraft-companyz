package com.github.donghune.companyz.combination.model

import com.github.donghune.companyz.util.extension.toCloneTypeArray
import com.github.donghune.namulibrary.extension.ItemBuilder
import com.github.donghune.namulibrary.extension.toMoneyFormat
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack

@SerializableAs("Recipe")
data class Recipe(
    val id: String,
    var display: String,
    var description: List<String>,
    var materials: List<ItemStack>,
    var resultItem: ItemStack,
    var recipeShopInfo: RecipeShopInfo
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

    fun toSellRecipeItemStack(): ItemStack {
        return toItemStack().clone().apply {
            (lore() ?: mutableListOf()).apply {
                add(Component.text(""))
                add(Component.text("수량제한 : ${if (recipeShopInfo.isUnlimitedSales) "무제한" else "한정"}"))
                add(Component.text("구매가격 : ${recipeShopInfo.price.toMoneyFormat()}"))
            }.also { lore(it) }
        }
    }

    private fun toMaterialLore(): List<String> {
        val itemMap = mutableMapOf<String, Int>()
        materials.toCloneTypeArray().forEach { itemStack: ItemStack ->
            val key = itemStack.i18NDisplayName ?: itemStack.type.toString()
            itemMap[key] = (itemMap[key] ?: 0) + itemStack.amount
        }
        return itemMap
            .map { (it.key.ifEmpty { it.key }) to it.value }
            .map { pair: Pair<String, Int> -> "${pair.first}x${pair.second}" }
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "display" to display,
            "description" to description,
            "materials" to materials,
            "resultItem" to resultItem,
            "recipeShopInfo" to recipeShopInfo,
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
                data["recipeShopInfo"] as RecipeShopInfo,
            )
        }
    }
}