package com.hammer.app.subwaysimulator.localdata

enum class Breads(val breadName: String, val price: Int = 0, val isEnabled:Boolean = true) {
    WHEAT("ウィート"),
    WHITE("ホワイト"),
    SESAME("セサミ"),
    HONEY_OATES("ハニーオーツ"),
    FLAT_BREAD("フラットブレッド"),
    NONE("無し(サラダ, + 300円)", 300),

    //期間終了
    ONION_SESAME("オニオンセサミペッパー(期間限定)",  isEnabled = false),
}

val breads = Breads.values().filter { it.isEnabled }.map { it.breadName }.toTypedArray()