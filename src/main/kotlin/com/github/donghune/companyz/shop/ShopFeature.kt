package com.github.donghune.companyz.shop

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.shop.command.ShopCommand
import com.github.donghune.companyz.shop.model.Shop
import com.github.donghune.companyz.shop.model.ShopRepository
import com.github.donghune.companyz.shop.model.ShopStuff
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.companyz.util.struct.Feature
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.event.Listener
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class ShopFeature : Feature() {
    override val commands: List<Command> = listOf(
        ShopCommand()
    )

    override val listeners: List<Listener> = listOf(

    )

    override val modules: Module = module {
        single {
            ShopRepository(
                Shop::class.java,
                File("${plugin.dataFolder.absolutePath}/shop/shops")
            )
        }
    }

    override val serializableClazzs: List<Class<out ConfigurationSerializable>> = listOf(
        Shop::class.java,
        ShopStuff::class.java
    )
}