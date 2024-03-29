package com.hammer.app.subwaysimulator.common.util

import android.text.Spanned
import android.text.InputFilter

class MinMaxFilter(minValue: String, maxValue: String) : InputFilter {

    private var mIntMin: Int = 0
    private var mIntMax: Int = 0

    init {
        this.mIntMin = Integer.parseInt(minValue)
        this.mIntMax = Integer.parseInt(maxValue)
    }

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        try {
            val input = Integer.parseInt(dest.toString() + source.toString())
            if (isInRange(mIntMin, mIntMax, input))
                return null
        } catch (nfe: NumberFormatException) {
        }

        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}
