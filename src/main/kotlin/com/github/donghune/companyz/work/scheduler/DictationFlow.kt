package com.github.donghune.companyz.work.scheduler

import com.github.donghune.companyz.plugin
import com.github.donghune.companyz.work.extension.complete
import com.github.donghune.companyz.work.extension.partTimeJob
import com.github.donghune.namulibrary.extension.replaceChatColorCode
import io.papermc.paper.event.player.AsyncChatEvent
import kotlinx.coroutines.delay
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class DictationFlow(val player: Player, content: String) : Listener {

    private val words = content.split(" ")
    private var currentWord = ""
    private var correctAnswers = 0
    private var isAlreadyEnter = false
    private var dictationStatus = DictationStatus.PENDING

    suspend fun launch() {
        player.closeInventory()
        player.sendActionBar(Component.text("&l&c화면에 나타나는 단어를 채팅창에 입력해 주세요.".replaceChatColorCode()))

        player.sendTitle("", "&l&c지금부터 기자회견을 시작하겠습니다.".replaceChatColorCode(), 0, 60, 0)
        delay(3000L)

        player.sendTitle("", "3", 0, 20, 0)
        delay(1000L)

        player.sendTitle("", "2", 0, 20, 0)
        delay(1000L)

        player.sendTitle("", "1", 0, 20, 0)
        delay(1000L)

        Bukkit.getPluginManager().registerEvents(this, plugin)

        words.forEach { word ->
            isAlreadyEnter = false
            currentWord = word
            dictationStatus = DictationStatus.PENDING
            repeat(WORD_DELAY * 10) {
                player.sendTitle(
                    "${dictationStatus.chatColor}$word",
                    "%.1fs".format((WORD_DELAY - (it / 10.0))),
                    0, WORD_DELAY * 20,
                    0
                )
                delay(100L)
            }
        }

        player.partTimeJob?.complete(player, correctAnswers / words.size.toDouble())
        AsyncChatEvent.getHandlerList().unregister(this)
    }

    @EventHandler
    fun onAsyncChatEvent(event: AsyncChatEvent) {
        if (event.player.uniqueId != player.uniqueId) {
            return
        }

        event.isCancelled = true

        if (isAlreadyEnter) {
            return
        }

        isAlreadyEnter = true
        val message = event.message()

        if (message == Component.text(currentWord)) {
            correctAnswers++
            dictationStatus = DictationStatus.SUCCESS
            return
        }

        dictationStatus = DictationStatus.FAIL
    }

    companion object {
        private const val WORD_DELAY = 2
    }

}