package com.hammer.app.subwaysimulator.model

import com.hammer.app.subwaysimulator.localdata.AmountsDressing
import com.hammer.app.subwaysimulator.localdata.Dressings

data class Dressing(
    val type: Dressings,
    val amounts: AmountsDressing
)