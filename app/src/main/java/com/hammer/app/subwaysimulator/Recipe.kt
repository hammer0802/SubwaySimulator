package com.hammer.app.subwaysimulator

class Recipe {
    var name = ""  //レシピ名
    var price = 0  //合計金額
    var sandwich = "" //サンドウィッチの種類
    var bread = ""  //パンの種類
    //トッピング
    var cheese = false
    var cream = false
    var mascar = false
    var egg = false
    var bacon = false
    var tuna = false
    var shrimp = false
    var avocado = false
    //野菜
    var lettuce = Amount.middle
    var tomato = Amount.middle
    var greenpepper = Amount.middle
    var redonion = Amount.middle
    var carrot = Amount.middle
    var olive = Amount.no
    var pickles = Amount.no
    var hotpepper = Amount.no
    var dressing = ""  //ドレッシングの種類
}


