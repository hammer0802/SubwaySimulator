package com.hammer.app.subwaysimulator.localdata

enum class Amounts(val amount: String){
    NONE("無し"),
    LITTLE("少なめ"),
    NORMAL("普通"),
    MANY("多め"),
    MAX("上限");

    companion object {
        fun from(amount : String) : Amounts? {
            return when(amount) {
                NONE.amount -> NONE
                LITTLE.amount -> LITTLE
                NORMAL.amount -> NORMAL
                MANY.amount -> MANY
                MAX.amount -> MAX
                else -> null
            }
        }
    }
}

enum class AmountsDressing(val amount: String){
    LITTLE("少なめ"),
    NORMAL("普通"),
    MANY("多め"),
}

val amounts = Amounts.values().map { it.amount }.toTypedArray()

val amountsDressing = AmountsDressing.values().map { it.amount }.toTypedArray()