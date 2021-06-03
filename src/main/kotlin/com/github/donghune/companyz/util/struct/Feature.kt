package com.github.donghune.companyz.util.struct

import org.bukkit.event.Listener
import org.koin.core.module.Module

abstract class Feature {
    abstract val commands: List<Command>
    abstract val listeners: List<Listener>
    abstract val modules: Module
}