package com.hammer.app.subwaysimulator.model

class Sandwich(val name :String, val price :Int, val recoommendDressing :String, val isEnabled :Boolean = true){
    companion object {
        fun createMenus() = listOf(
                    Sandwich("BLT", 420, "野菜クリーミー")
        )
    }
}


class Bread(){}

class Topping(){}

class Vegetable(){}

class Dressing(){}