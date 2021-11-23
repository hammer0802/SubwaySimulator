package com.hammer.app.subwaysimulator.model

import java.util.UUID

data class Recipe (
    val recipeId: RecipeId = RecipeId.createRecipeId(),
    var name: String = "",  //レシピ名
    var price: Int = 0,  //合計金額
    val sandwich: Sandwich,
    val bread: Bread,
    val toppingList: List<Topping>,

    //野菜
    var lettuce: String = "",
    var tomato: String = "",
    var greenpepper: String = "",
    var redonion: String = "",
    var carrot: String = "",
    var olive: String = "",
    var pickles: String = "",
    var hotpepper: String = "",
    var dressing: MutableList<String> = mutableListOf<String>(),  //ドレッシングの種類
    var dressingAmount: MutableList<String> = mutableListOf<String>(), //ドレッシングの量
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