package com.github.donghune.companyz.transportation.extension

import com.github.donghune.companyz.money.extension.money
import com.github.donghune.companyz.transportation.model.TransitPoint
import com.github.donghune.companyz.transportation.model.TransitPointRepository
import com.github.donghune.companyz.util.extension.syncTeleport
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.donghune.namulibrary.extension.toMoneyFormat
import org.bukkit.Location
import org.bukkit.entity.Player

fun TransitPoint.calculatePrice(startLocation: Location): Int {
    return (location.distance(startLocation) * TransitPointRepository.METER_BY_PRICE / 100).toInt()
}

fun TransitPoint.use(player: Player) {

    val price = calculatePrice(player.location)

    if (player.money < price) {
        player.sendErrorMessage("대중교통을 이용하기엔 보유금액이 부족합니다.")
        return
    }

    player.money -= price
    player.syncTeleport(location)
    player.sendInfoMessage("대중교통을 이용하여 보유금액이 차감되었습니다. -${price.toMoneyFormat()}")
}