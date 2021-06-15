package com.github.donghune.companyz.util.extension

import com.github.donghune.companyz.plugin
import com.github.shynixn.mccoroutine.minecraftDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

val Dispatchers.Minecraft: CoroutineContext
    get() = plugin.minecraftDispatcher
