package com.github.donghune.companyz.combination.command

import com.github.donghune.companyz.combination.inventory.MyRecipeInventory
import com.github.donghune.companyz.combination.inventory.RecipeMaterialEditInventory
import com.github.donghune.companyz.combination.inventory.RecipeShopInventory
import com.github.donghune.companyz.combination.model.Recipe
import com.github.donghune.companyz.combination.model.RecipeRepository
import com.github.donghune.companyz.util.struct.Command
import com.github.donghune.namulibrary.extension.sendErrorMessage
import com.github.donghune.namulibrary.extension.sendInfoMessage
import com.github.monun.kommand.KommandDispatcherBuilder
import com.github.monun.kommand.argument.bool
import com.github.monun.kommand.argument.integer
import com.github.monun.kommand.argument.string
import org.bukkit.Material
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.inject

class CombinationCommand : Command() {

    private val recipeRepository by inject<RecipeRepository>(RecipeRepository::class.java)

    override val command: KommandDispatcherBuilder.() -> Unit
        get() = {
            register("combination") {
                then("open") {
                    executes {
                        val player = it.sender as Player
                        MyRecipeInventory(player).open(player)
                    }
                }
                then("shop") {
                    executes {
                        val player = it.sender as Player
                        RecipeShopInventory().open(player)
                    }
                }
                then("add") {
                    then("recipeName" to string()) {
                        executes {
                            val player = it.sender as Player
                            val recipeName = it.parseArgument<String>("recipeName")

                            if (recipeRepository.get(recipeName) != null) {
                                player.sendErrorMessage("이미 존재하는 레시피 아이디 입니다.")
                                return@executes
                            }

                            recipeRepository.createDefaultData(recipeName)
                            player.sendInfoMessage("레시피를 생성 하였습니다.")
                        }
                    }
                }
                then("remove") {
                    then("recipe" to RecipeArgument) {
                        executes {
                            val player = it.sender as Player
                            val recipe = it.parseArgument<Recipe>("recipe")

                            recipeRepository.remove(recipe.id)
                            player.sendInfoMessage("레시피를 삭제 하였습니다.")
                        }
                    }
                }
                then("modify") {
                    then("recipe" to RecipeArgument) {
                        then("display") {
                            then("value" to string()) {
                                executes {
                                    val player = it.sender as Player
                                    val recipe = it.parseArgument<Recipe>("recipe")
                                    val value = it.parseArgument<String>("value")

                                    recipe.display = value
                                    recipeRepository.save(recipe.id)
                                    player.sendInfoMessage("레시피 정보를 수정하였습니다.")
                                }
                            }
                        }
                        then("description") {
                            then("add") {
                                then("value" to string()) {
                                    executes {
                                        val player = it.sender as Player
                                        val recipe = it.parseArgument<Recipe>("recipe")
                                        val value = it.parseArgument<String>("value")

                                        recipe.description.toMutableList()
                                            .apply { add(value) }
                                            .also { description -> recipe.description = description }
                                        recipeRepository.save(recipe.id)
                                        player.sendInfoMessage("레시피 정보를 수정하였습니다.")
                                    }
                                }
                            }
                            then("remove") {
                                then("index" to integer()) {
                                    executes {
                                        val player = it.sender as Player
                                        val recipe = it.parseArgument<Recipe>("recipe")
                                        val index = it.parseArgument<Int>("index")

                                        recipe.description.toMutableList()
                                            .apply { removeAt(index) }
                                            .also { description -> recipe.description = description }
                                        recipeRepository.save(recipe.id)
                                        player.sendInfoMessage("레시피 정보를 수정하였습니다.")
                                    }
                                }
                            }
                            then("set") {
                                then("index" to integer()) {
                                    then("value" to string()) {
                                        executes {
                                            val player = it.sender as Player
                                            val recipe = it.parseArgument<Recipe>("recipe")
                                            val index = it.parseArgument<Int>("index")
                                            val value = it.parseArgument<String>("value")

                                            recipe.description.toMutableList()
                                                .apply { set(index, value) }
                                                .also { description -> recipe.description = description }
                                            recipeRepository.save(recipe.id)
                                            player.sendInfoMessage("레시피 정보를 수정하였습니다.")
                                        }
                                    }
                                }
                            }
                        }
                        then("materials") {
                            then("value" to string()) {
                                executes {
                                    val player = it.sender as Player
                                    val recipe = it.parseArgument<Recipe>("recipe")

                                    RecipeMaterialEditInventory(recipe).open(player)
                                }
                            }
                        }
                        then("resultItem") {
                            executes {
                                val player = it.sender as Player
                                val recipe = it.parseArgument<Recipe>("recipe")
                                val resultItem = player.inventory.itemInMainHand

                                if (resultItem.type == Material.AIR) {
                                    player.sendErrorMessage("손에 든 아이템이 존재하지 않습니다.")
                                    return@executes
                                }

                                recipe.resultItem = resultItem
                                recipeRepository.save(recipe.id)
                                player.sendInfoMessage("레시피 정보를 수정하였습니다.")
                            }
                        }
                        then("recipeShopInfo") {
                            then("isUnlimitedSales") {
                                then("value" to bool()) {
                                    executes {
                                        val player = it.sender as Player
                                        val recipe = it.parseArgument<Recipe>("recipe")
                                        val value = it.parseArgument<Boolean>("value")

                                        recipe.recipeShopInfo.isUnlimitedSales = value
                                        recipeRepository.save(recipe.id)
                                        player.sendInfoMessage("레시피 정보를 수정하였습니다.")
                                    }
                                }
                            }
                            then("price") {
                                then("value" to integer()) {
                                    executes {
                                        val player = it.sender as Player
                                        val recipe = it.parseArgument<Recipe>("recipe")
                                        val value = it.parseArgument<Int>("value")

                                        recipe.recipeShopInfo.price = value
                                        recipeRepository.save(recipe.id)
                                        player.sendInfoMessage("레시피 정보를 수정하였습니다.")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
}