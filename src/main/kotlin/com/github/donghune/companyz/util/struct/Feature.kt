package com.github.donghune.companyz.util.struct

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.event.Listener
import org.koin.core.module.Module
import kotlin.reflect.KClass

abstract class Feature {
    abstract val commands: List<Command>
    abstract val listeners: List<Listener>
    abstract val modules: Module
    abstract val serializableClazzs: List<Class<out ConfigurationSerializable>>
}