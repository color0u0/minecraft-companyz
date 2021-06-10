package com.github.donghune.companyz.combination

import com.github.donghune.companyz.combination.command.CombinationCommand
import com.github.donghune.companyz.combination.listener.CombinationListener
import com.github.donghune.companyz.combination.model.*
import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.companyz.util.struct.Feature
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.event.Listener
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class CombinationFeature : Feature() {
    override val commands: List<Command> = listOf(
        CombinationCommand()
    )

    override val listeners: List<Listener> = listOf(
        CombinationListener()
    )

    override val modules: Module = module {
        single {
            RecipeRepository(
                Recipe::class.java,
                File("${plugin.dataFolder.absolutePath}/recipe/recipes")
            )
        }
        single {
            PlayerRecipeRepository(
                PlayerRecipe::class.java,
                File("${plugin.dataFolder.absolutePath}/recipe/players")
            )
        }
    }

    override val serializableClazzs: List<Class<out ConfigurationSerializable>> = listOf(
        Recipe::class.java,
        RecipeShopInfo::class.java,
        PlayerRecipe::class.java,
    )
}