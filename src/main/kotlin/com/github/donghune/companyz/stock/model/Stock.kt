package com.github.donghune.companyz.stock.model

import com.github.donghune.companyz.stock.extension.save
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.SerializableAs

@SerializableAs("Stock")
data class Stock(
    val name: String,
    var tradePrice: Int,
    var openingPrice: Int,
) : ConfigurationSerializable {

    fun fluctuatePrice(min: Int, max: Int): Stock {
        val value = ((min * 100)..(max * 100)).random() / 10000.0
        tradePrice += (tradePrice * value).toInt()
        save()
        return this
    }

    override fun serialize(): Map<String, Any> {
        return mapOf(
            "name" to name,
            "tradePrice" to tradePrice,
            "openingPrice" to openingPrice,
        )
    }

    companion object {
        @JvmStatic
        fun deserialize(data: Map<String, Any>): Stock {
            return Stock(
                data["name"] as String,
                data["tradePrice"] as Int,
                data["openingPrice"] as Int
            )
        }
    }

}