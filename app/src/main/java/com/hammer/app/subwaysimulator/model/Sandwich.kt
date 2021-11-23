package com.hammer.app.subwaysimulator.model

import com.hammer.app.subwaysimulator.localdata.Sandwiches

data class Sandwich(
    val type: Sandwiches,
    val isFootLong: Boolean
) {
    companion object {
        fun from(typeName: String, isFootLong: Boolean): Sandwich {
            val type = Sandwiches.values().first { it.sandName == typeName }
            return Sandwich(type, isFootLong)
        }
    }
}