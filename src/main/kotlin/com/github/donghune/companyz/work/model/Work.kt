package com.github.donghune.companyz.work.model

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("Work")
data class Work(
    val name: String,
    var display: String,
    var description: String,
    var mission: WorkMission,
    var workType: WorkType,
    var reward: WorkReward,
) : ConfigurationSerializable {
    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "display" to display,
            "description" to description,
            "mission" to mission,
            "workType" to workType.toString(),
            "reward" to reward,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Work {
            return Work(
                data["name"] as String,
                data["display"] as String,
                data["description"] as String,
                data["mission"] as WorkMission,
                WorkType.valueOf(data["workType"] as String),
                data["reward"] as WorkReward,
            )
        }
    }
}

