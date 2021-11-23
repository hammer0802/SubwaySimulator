package com.hammer.app.subwaysimulator.model

import com.hammer.app.subwaysimulator.localdata.AccentVegetables
import com.hammer.app.subwaysimulator.localdata.Amounts
import com.hammer.app.subwaysimulator.localdata.Vegetables
import java.util.UUID

data class Recipe (
    val recipeId: RecipeId = RecipeId.createRecipeId(),
    var name: String = "",  //レシピ名
    var price: Int = 0,  //合計金額
    val sandwich: Sandwich,
    val bread: Bread,
    val toppingList: List<Topping>,
    val vegetableMap: Map<Vegetables, Amounts>,
    val accentVegetableMap: Map<AccentVegetables, Amounts>,
    val dressing: List<Dressing>,
    var howToDress: String = "",
    var createTime: String = "", //作成日時
    var editTime: String = "", //編集日時
)

@JvmInline
value class RecipeId(val id: String) {
    companion object {
        fun createRecipeId() : RecipeId {
            return RecipeId(UUID.randomUUID().toString())
        }
    }
}