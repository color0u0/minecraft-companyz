package com.github.donghune.companyz.money

import com.github.donghune.companyz.util.struct.Feature
import com.github.donghune.companyz.money.command.MoneyCommand
import com.github.donghune.companyz.money.listener.MoneyListener
import com.github.donghune.companyz.money.model.PlayerMoney
import com.github.donghune.companyz.money.model.PlayerMoneyRepository
import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.util.struct.Command
import org.bukkit.event.Listener
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class MoneyFeature : Feature() {

    override val commands: List<Command> = listOf(
        MoneyCommand()
    )

    override val listeners: List<Listener> = listOf(
        MoneyListener()
    )

    override val modules: Module = module {
        single {
            PlayerMoneyRepository(
                PlayerMoney::class.java,
                File("${plugin.dataFolder.absolutePath}/money/players")
            )
        }
    }

}