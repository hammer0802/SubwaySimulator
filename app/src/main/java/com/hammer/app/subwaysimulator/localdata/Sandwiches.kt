package com.hammer.app.subwaysimulator.localdata

enum class Sandwiches(val sandName: String, val price: Int, val recommendDressing: Dressings, val isEnabled: Boolean = true) {
    BLT("BLT", 420, Dressings.CAESAR),
    EBI_AVOCADO("えびアボカド", 500, Dressings.CREAMY),
    NAMA_HAM("生ハム＆マスカルポーネ", 520, Dressings.BASIL),
    ROAST_CHICKEN("ローストチキン", 420, Dressings.HONEY_MUSTARD),
    ROAST_BEEF("ローストビーフ　～プレミアム製法～", 590, Dressings.WASABI),
    TURKEY_BACON_EGG("ターキーベーコンエッグ", 490, Dressings.CAESAR),
    CHEESE_ROAST_CHICKEN("チーズローストチキン", 480, Dressings.NONE),
    TERIYAKI_CHICKEN("てり焼きチキン", 480, Dressings.MAYONNAISE),
    CHILI_CHICKEN("チリチキン", 410, Dressings.MAYONNAISE),
    TURKEY("ターキーブレスト", 450, Dressings.BALSAMICO),
    TUNA("ツナ", 430, Dressings.PEPPER),
    EGG("たまご", 390, Dressings.SALT_PEPPER),
    AVOCADO_VEGE("アボカドベジー", 410, Dressings.CREAMY),
    VEGE_CHEESE("ベジー＆チーズ", 340, Dressings.CREAMY),
    VEGE_DE_LIGHT("ベジーデライト", 300, Dressings.OIL_VINEGAR),
    MOZZARELLA_CHICKEN("大人モッツァレラ・チキン(期間限定)", 490, Dressings.BASIL),

    // 期間終了
    KINKAKU_BURG("金格バーグ(期間限定)", 890, Dressings.WAFU, false),
    KINKAKU_DX("金格DX(期間限定)", 990, Dressings.WAFU, false),
    BACON_CHICKEN_MELT("ベーコンチキンメルト(期間限定)", 510, Dressings.RANCH, false),
    CHICKEN_MELT("チキンメルト(期間限定)", 450, Dressings.RANCH, false),
}

enum class FootLong(val price: Int) {
    FOOT_LONG(300)
}

val sandwiches = Sandwiches.values().filter { it.isEnabled }.map { it.sandName }.toTypedArray()
