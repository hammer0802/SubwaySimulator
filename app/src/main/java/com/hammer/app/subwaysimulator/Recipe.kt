package com.hammer.app.subwaysimulator

class Recipe {
    var name = ""  //レシピ名
    var price = 0  //合計金額
    var sandwich = "" //サンドウィッチの種類
    var footLong = false
    var bread = ""  //パンの種類
    var toast = false //トーストの有無
    //トッピング、煩雑なのでToppingクラスでまとめる？
    var cheese = false
    var cream = false
    var mascar = false
    var egg = false
    var bacon = false
    var tuna = false
    var shrimp = false
    var avocado = false
    var roastbeef = false
    var cheeseAmount = 1
    var creamAmount = 1
    var mascarAmount = 1
    var eggAmount = 1
    var baconAmount = 1
    var tunaAmount = 1
    var shrimpAmount = 1
    var avocadoAmount = 1
    var roastbeefAmount = 1
    //野菜
    var lettuce = ""
    var tomato = ""
    var greenpepper = ""
    var redonion = ""
    var carrot = ""
    var olive = ""
    var pickles = ""
    var hotpepper = ""
    var dressing = mutableListOf<String>()  //ドレッシングの種類
    var dressingAmount = mutableListOf<String>() //ドレッシングの量
    var howToDress = ""
    var createTime = "" //作成日時
    var editTime = "" //編集日時
    var uuid = "" //UUID
}