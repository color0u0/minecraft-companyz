package com.github.donghune

import com.github.namu0240.inventory.GUI
import com.github.shynixn.mccoroutine.SuspendingJavaPlugin

class SamplePlugin : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        println("Hello, World!")
    }

    override suspend fun onDisableAsync() {
    }
}