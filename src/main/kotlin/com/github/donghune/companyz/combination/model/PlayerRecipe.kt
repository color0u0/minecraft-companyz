package com.github.donghune.companyz.combination.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import java.util.*

@SerializableAs("PlayerRecipe")
data class PlayerRecipe(
    val uuid: UUID,
    val recipes: List<String>
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "uuid" to uuid.toString(),
            "recipes" to recipes,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): PlayerRecipe {
            return PlayerRecipe(
                UUID.fromString(data["uuid"] as String),
                data["recipes"] as List<String>
            )
        }
    }
}