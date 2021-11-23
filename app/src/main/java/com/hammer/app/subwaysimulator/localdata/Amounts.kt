package com.hammer.app.subwaysimulator.localdata

enum class Amounts(val amount: String){
    NONE("無し"),
    LITTLE("少なめ"),
    NORMAL("普通"),
    MANY("多め"),
    MAX("上限");

    companion object {
        fun from(amount : String) : Amounts {
            return values().first { it.amount == amount }
        }
    }
}

enum class AmountsDressing(val amount: String){
    LITTLE("少なめ"),
    NORMAL("普通"),
    MANY("多め"),
    NONE("-");
    companion object {
        fun from(amount : String) : AmountsDressing {
            return values().first { it.amount == amount }
        }
    }
}

val amounts = Amounts.values().map { it.amount }.toTypedArray()

val amountsDressing = AmountsDressing.values().filter { it != AmountsDressing.NONE }.map { it.amount }.toTypedArray()