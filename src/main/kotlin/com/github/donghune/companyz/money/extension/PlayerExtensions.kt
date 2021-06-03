package com.github.donghune.companyz.money.extension

import com.github.donghune.companyz.money.model.PlayerMoneyRepository
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject

private val repository by inject<PlayerMoneyRepository>(PlayerMoneyRepository::class.java)

var Player.money: Int
    get() {
        return repository.getSafety(uniqueId.toString()).money
    }
    set(value) {
        val playerMoney = repository.getSafety(uniqueId.toString())
        playerMoney.money = value
        if (playerMoney.money < 0) {
            playerMoney.money = 0
        }
        repository.update(playerMoney.uuid.toString(), playerMoney)
    }