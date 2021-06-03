package com.github.donghune.companyz.util.extension

import com.github.donghune.companyz.plugin
import com.github.shynixn.mccoroutine.launch
import com.github.shynixn.mccoroutine.minecraftDispatcher
import org.bukkit.Location
import org.bukkit.entity.Entity

fun Entity.syncTeleport(location: Location) {
    plugin.launch(plugin.minecraftDispatcher) {
        teleport(location)
    }
}

fun Entity.syncTeleport(entity: Entity) {
    plugin.launch(plugin.minecraftDispatcher) {
        teleport(entity)
    }
}