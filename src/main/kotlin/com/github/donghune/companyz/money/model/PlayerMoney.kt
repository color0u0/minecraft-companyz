package com.github.donghune.companyz.money.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("PlayerMoney")
data class PlayerMoney(
        val uuid: UUID,
        var money: Int
) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        return mapOf(
                "uuid" to uuid.toString(),
                "money" to money,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): PlayerMoney {
            return PlayerMoney(
                    UUID.fromString(data["uuid"] as String),
                    data["money"] as Int,
            )
        }
    }

}