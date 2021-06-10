package com.github.donghune.companyz.combination.command

import com.github.donghune.companyz.combination.model.Recipe
import com.github.donghune.companyz.combination.model.RecipeRepository
import com.github.monun.kommand.KommandContext
import com.github.monun.kommand.argument.KommandArgument
import com.github.monun.kommand.argument.suggest
import org.koin.java.KoinJavaComponent.inject

object RecipeArgument : KommandArgument<Recipe> {

    private val recipeRepository by inject<RecipeRepository>(RecipeRepository::class.java)

    override val parseFailMessage: String
        get() = "${KommandArgument.TOKEN} <-- 해당 레시피를 찾지 못했습니다."

    override fun parse(context: KommandContext, param: String): Recipe? {
        return recipeRepository.get(param)
    }

    override fun suggest(context: KommandContext, target: String): Collection<String> {
        return recipeRepository.getList().suggest(target) { it.id }
    }
}