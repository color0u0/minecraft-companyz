package com.github.donghune.companyz.combination.command

import com.github.donghune.companyz.util.struct.Command
import com.github.monun.kommand.KommandDispatcherBuilder

class CombinationCommand : Command() {
    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {

        }
}