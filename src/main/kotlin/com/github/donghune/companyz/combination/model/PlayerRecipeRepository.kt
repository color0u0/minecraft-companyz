package com.github.donghune.companyz.combination.model

import com.github.donghune.namulibrary.model.EntityRepository
import java.io.File
import java.util.*

class PlayerRecipeRepository(
    override val dataType: Class<PlayerRecipe>,
    override val file: File
) : EntityRepository<PlayerRecipe>() {
    override fun getDefaultData(key: String): PlayerRecipe {
        return PlayerRecipe(UUID.fromString(key), mutableListOf())
    }
}