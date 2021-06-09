package com.github.donghune.companyz.work.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs
import org.bukkit.inventory.ItemStack

@SerializableAs("WorkMission")
data class WorkMission(
    var toWhom: String,
    var itemStacks: List<ItemStack>,
    var textContent: String
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "toWhom" to toWhom,
            "itemStacks" to itemStacks,
            "textContent" to textContent,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): WorkMission {
            return WorkMission(
                data["toWhom"] as String,
                data["itemStacks"] as List<ItemStack>,
                data["textContent"] as String,
            )
        }
    }
}