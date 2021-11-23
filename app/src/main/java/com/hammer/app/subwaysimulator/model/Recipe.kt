package com.hammer.app.subwaysimulator.model

import java.util.UUID

data class Recipe (
    val recipeId: RecipeId = RecipeId.createRecipeId(),
    var name: String = "",  //レシピ名
    var price: Int = 0,  //合計金額
    var sandwich: String = "", //サンドウィッチの種類
    var footLong: Boolean = false,
    var bread: String = "",  //パンの種類
    var toast: Boolean = false, //トーストの有無
    //トッピング、煩雑なのでToppingクラスでまとめる？
    var cheese: Boolean = false,
    var cream: Boolean = false,
    var mascar: Boolean = false,
    var egg: Boolean = false,
    var bacon: Boolean = false,
    var tuna: Boolean = false,
    var shrimp: Boolean = false,
    var avocado: Boolean = false,
    var roastbeef: Boolean = false,
    var cheeseAmount: Int = 1,
    var creamAmount: Int = 1,
    var mascarAmount: Int = 1,
    var eggAmount: Int = 1,
    var baconAmount: Int = 1,
    var tunaAmount: Int = 1,
    var shrimpAmount: Int = 1,
    var avocadoAmount: Int = 1,
    var roastbeefAmount: Int = 1,

    //期間限定トッピング
    var shredded: Boolean = false,
    var shreddedAmount: Int = 1,

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