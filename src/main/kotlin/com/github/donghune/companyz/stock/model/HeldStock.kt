package com.github.donghune.companyz.stock.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("HeldStock")
data class HeldStock(
    val name: String,
    var amount: Int,
    var buyPrice: Int
) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "amount" to amount,
            "buyPrice" to buyPrice,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): HeldStock {
            return HeldStock(
                data["name"] as String,
                data["amount"] as Int,
                data["buyPrice"] as Int,
            )
        }
    }

}