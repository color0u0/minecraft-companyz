package com.github.donghune.companyz.shop.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("Shwop")
data class Shop(
    val name: String,
    val stuffList: MutableList<ShopStuff>,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "stuffList" to stuffList,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Shop {
            return Shop(
                data["name"] as String,
                data["stuffList"] as MutableList<ShopStuff>,
            )
        }
    }
}