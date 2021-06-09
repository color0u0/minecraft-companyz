package com.github.donghune.companyz.work.scheduler

import org.bukkit.ChatColor

enum class DictationStatus(val chatColor: ChatColor) {

    PENDING(ChatColor.WHITE),
    SUCCESS(ChatColor.BLUE),
    FAIL(ChatColor.RED)

}