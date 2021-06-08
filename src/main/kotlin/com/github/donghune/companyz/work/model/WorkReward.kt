package com.github.donghune.companyz.work.model

import org.bukkit.configuration.serialization.ConfigurationSerializable

data class WorkReward(
    var reputation: Int,
    var money: Int,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "reputation" to reputation,
            "money" to money,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): WorkReward {
            return WorkReward(
                data["reputation"] as Int,
                data["money"] as Int,
            )
        }
    }
}

