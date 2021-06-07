package com.github.donghune.companyz.shop.extension

import com.github.donghune.companyz.shop.model.Shop
import com.github.donghune.companyz.shop.model.ShopRepository
import com.github.donghune.companyz.shop.model.ShopStuff
import org.koin.java.KoinJavaComponent.inject

private val repository by inject<ShopRepository>(ShopRepository::class.java)

fun Shop.addStuff(stuff: ShopStuff) {
    stuff.itemStack.apply { amount = 1 }
    stuffList.add(stuff)
    repository.save(name)
}

fun Shop.removeStuff(index: Int) {
    stuffList.removeAt(index)
    repository.save(name)
}