package com.github.donghune.companyz.stock

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.stock.command.StockCommand
import com.github.donghune.companyz.stock.listener.StockListener
import com.github.donghune.companyz.stock.model.Stock
import com.github.donghune.companyz.stock.model.StockRepository
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.companyz.util.struct.Feature
import org.bukkit.event.Listener
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

class StockFeature : Feature() {
    override val commands: List<Command> = listOf(
        StockCommand()
    )

    override val listeners: List<Listener> = listOf(
        StockListener()
    )

    override val modules: Module = module {
        single {
            StockRepository(
                Stock::class.java,
                File("${plugin.dataFolder.absolutePath}/stock/stocks")
            )
        }
    }
}