package com.hammer.app.subwaysimulator.localdata

enum class Toppings(val toppingName: String, val price: Int, val engName: String, val isEnabled:Boolean = true) {
    NATURAL_CHEESE("ナチュラルスライスチーズ(+ 40円)", 40, "cheese"),
    CREAM_CHEESE("クリームタイプチーズ(+ 60円)", 60, "cream"),
    MASCARPONE_CHEESE("マスカルポーネチーズ(+ 90円)", 90, "mascar"),
    EGG("たまご(+ 60円)", 60, "egg"),
    BACON("ベーコン(+ 60円)",60, "bacon"),
    TUNA("ツナ(+ 80円)",80, "tuna"),
    SHRIMP("えび(+ 100円)",100, "shrimp"),
    AVOCADO("アボカド(+ 110円)",110,"avocado"),
    ROAST_BEEF("ローストビーフ(+ 350円)", 350, "roastbeef"),

    //期間終了
    SHREDDED_CHEESE("シュレッドチーズ(+ 40円)", 40, "shredded", false),
}

val toppings = Toppings.values().filter { it.isEnabled }.map { it.toppingName }.toTypedArray()