package com.github.donghune.companyz.work

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.companyz.util.struct.Feature
import com.github.donghune.companyz.work.command.WorkCommand
import com.github.donghune.companyz.work.listener.WorkListener
import com.github.donghune.companyz.work.model.*
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.event.Listener
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class WorkFeature : Feature() {
    override val commands: List<Command> = listOf(
        WorkCommand()
    )

    override val listeners: List<Listener> = listOf(
        WorkListener()
    )

    override val modules: Module = module {
        single {
            WorkRepository(
                Work::class.java,
                File(plugin.dataFolder.absolutePath + "/work/works")
            )
        }

        single {
            PartTimeWorkRepository()
        }
    }

    override val serializableClazzs: List<Class<out ConfigurationSerializable>> = listOf(
        Work::class.java,
        WorkMission::class.java,
        WorkReward::class.java,
    )
}