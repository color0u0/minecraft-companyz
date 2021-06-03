package com.github.donghune.companyz.util.extension

import com.github.shynixn.mccoroutine.SuspendingJavaPlugin
import com.github.shynixn.mccoroutine.registerSuspendingEvents
import org.bukkit.Bukkit
import org.bukkit.event.Listener

fun Listener.invoke(plugin: SuspendingJavaPlugin) {
    Bukkit.getPluginManager().registerSuspendingEvents(this, plugin)
}

