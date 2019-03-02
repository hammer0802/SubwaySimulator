package com.hammer.app.subwaysimulator.model

import java.util.*

class Recipe {
    var name = ""  //レシピ名
    var sandwich = "" //サンドウィッチの種類
    var footLong = false
    var bread = ""  //パンの種類
    var toast = false //トーストの有無

    val toppings = mapOf<String, Int>() //トッピング

    val vegetables = mapOf<String, Amount>() //野菜

    val dressings = mapOf<String, Amount>()  //ドレッシング

    var howToDress = HowToDress.SEPARATE
    var editTime :Date = Date() //編集日時

    val price get() = 10 //TODO:料金計算
}