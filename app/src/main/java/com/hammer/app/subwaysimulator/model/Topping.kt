package com.hammer.app.subwaysimulator.model

import com.hammer.app.subwaysimulator.localdata.Amounts
import com.hammer.app.subwaysimulator.localdata.Toppings

data class Topping(
    val type: Toppings,
    val amount: Int
) {
    companion object {
        fun from(type: Toppings, amount: Int): Topping {
            return Topping(type, amount)
        }
    }
}