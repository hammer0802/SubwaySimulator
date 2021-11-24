package com.hammer.app.subwaysimulator.model

import com.hammer.app.subwaysimulator.localdata.Breads
import kotlinx.serialization.Serializable

@Serializable
data class Bread(
    val type: Breads,
    val isToasted: Boolean
) {
    companion object{
        fun from(typeName: String, isToasted: Boolean): Bread{
            val type = Breads.values().first{ it.breadName == typeName }
            return Bread(type, isToasted)
        }
    }
}