package com.hammer.app.subwaysimulator.model

import com.hammer.app.subwaysimulator.localdata.AmountsDressing
import com.hammer.app.subwaysimulator.localdata.Dressings
import kotlinx.serialization.Serializable

@Serializable
data class Dressing(
    val type: Dressings,
    val amounts: AmountsDressing
)