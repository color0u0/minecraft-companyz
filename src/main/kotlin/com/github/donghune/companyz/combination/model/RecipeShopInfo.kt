package com.github.donghune.companyz.combination.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("RecipeShopInfo")
class RecipeShopInfo(
    val isUnlimitedSales: Boolean,
    val price: Int
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "isUnlimitedSales" to isUnlimitedSales,
            "price" to price,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): RecipeShopInfo {
            return RecipeShopInfo(
                data["isUnlimitedSales"] as Boolean,
                data["price"] as Int
            )
        }
    }
}