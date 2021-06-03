package com.github.donghune.companyz.util.struct

import com.github.monun.kommand.KommandDispatcherBuilder

abstract class Command {
    abstract val command: KommandDispatcherBuilder.() -> Unit

    fun invoke(block: KommandDispatcherBuilder) {
        command.invoke(block)
    }
}