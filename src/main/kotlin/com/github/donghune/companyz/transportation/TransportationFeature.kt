package com.github.donghune.companyz.transportation

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.transportation.command.TransportationCommand
import com.github.donghune.companyz.transportation.model.TransitPoint
import com.github.donghune.companyz.transportation.model.TransitPointRepository
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.companyz.util.struct.Feature
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.event.Listener
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class TransportationFeature : Feature() {

    override val commands: List<Command> = listOf(
        TransportationCommand()
    )

    override val listeners: List<Listener> = listOf(

    )

    override val modules: Module = module {
        single {
            TransitPointRepository(
                TransitPoint::class.java,
                File("${plugin.dataFolder.absolutePath}/transportation/points")
            )
        }
    }

    override val serializableClazzs: List<Class<out ConfigurationSerializable>> = listOf(
        TransitPoint::class.java
    )

}