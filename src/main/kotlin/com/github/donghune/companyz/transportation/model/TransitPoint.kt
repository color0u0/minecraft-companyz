package com.github.donghune.companyz.transportation.model

import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("TransitPoint")
data class TransitPoint(
    val name: String,
    var display: String,
    var lore: List<String>,
    var location: Location
) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "display" to display,
            "lore" to lore,
            "location" to location,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): TransitPoint {
            return TransitPoint(
                data["name"] as String,
                data["display"] as String,
                data["lore"] as List<String>,
                data["location"] as Location,
            )
        }
    }
}