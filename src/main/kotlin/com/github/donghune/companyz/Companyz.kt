package com.github.donghune.companyz

import com.github.donghune.companyz.combination.CombinationFeature
import com.github.donghune.companyz.money.MoneyFeature
import com.github.donghune.companyz.shop.ShopFeature
import com.github.donghune.companyz.stock.StockFeature
import com.github.donghune.companyz.transportation.TransportationFeature
import com.github.donghune.companyz.util.command.ItemCommand
import com.github.donghune.companyz.work.WorkFeature
import com.github.donghune.namulibrary.extension.invoke
import com.github.monun.kommand.kommand
import com.github.shynixn.mccoroutine.SuspendingJavaPlugin
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin

lateinit var plugin: JavaPlugin

class Companyz : SuspendingJavaPlugin() {

    private val features = listOf(
        MoneyFeature(),
        StockFeature(),
        TransportationFeature(),
        ShopFeature(),
        WorkFeature(),
        CombinationFeature()
    )

    override suspend fun onEnableAsync() {
        plugin = this

        startKoin {
            kommand {
                ItemCommand().command.invoke(this)
                features.forEach {
                    it.commands.forEach { command -> command.invoke(this@kommand) }
                    it.listeners.forEach { listener -> listener.invoke(this@Companyz) }
                    modules(it.modules)
                    it.serializableClazzs.forEach { clazz ->
                        ConfigurationSerialization.registerClass(
                            clazz,
                            clazz.simpleName
                        )
                    }
                }
            }
        }

    }

}