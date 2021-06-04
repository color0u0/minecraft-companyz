package com.github.donghune.companyz.stock.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("PlayerStock")
data class PlayerStock(
    val uuid: UUID,
    val stocks: MutableMap<String, HeldStock>,
) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid.toString(),
            "stocks" to stocks,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): PlayerStock {
            return PlayerStock(
                UUID.fromString(data["uuid"] as String),
                data["stocks"] as MutableMap<String, HeldStock>,
            )
        }
    }

}