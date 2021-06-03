package com.github.donghune.companyz

import com.github.donghune.companyz.money.MoneyFeature
import com.github.donghune.companyz.util.extension.invoke
import com.github.donghune.companyz.util.struct.Feature
import com.github.monun.kommand.kommand
import com.github.shynixn.mccoroutine.SuspendingJavaPlugin
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin

lateinit var plugin: JavaPlugin

class Companyz : SuspendingJavaPlugin() {

    private val features = listOf<Feature>(
            MoneyFeature()
    )

    override suspend fun onEnableAsync() {
        plugin = this

        startKoin {
            kommand {
                features.forEach {
                    it.commands.forEach { command -> command.invoke(this@kommand) }
                    it.listeners.forEach { listener -> listener.invoke(this@Companyz) }
                    modules(it.modules)
                }
            }
        }

    }

    override suspend fun onDisableAsync() {
        super.onDisableAsync()
    }

}