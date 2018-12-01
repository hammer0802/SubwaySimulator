package com.hammer.app.subwaysimulator

enum class Sandwiches(val number: Int, val sandName: String, val price: Int, val recommendDressing: Int, val limitedFlag :Boolean){
    BLT(0,"BLT", 420, Dressings.CAESAR.number, false),
    EBI_AVOCADO(1,"えびアボカド", 500, Dressings.CREAMY.number, false),
    NAMA_HAM(2,"生ハム＆マスカルポーネ", 520, Dressings.BASIL.number, false),
    ROAST_CHICKEN(3, "ローストチキン", 420, Dressings.HONEY_MUSTARD.number, false),
    ROAST_BEEF(4,"ローストビーフ　～プレミアム製法～", 590, Dressings.WASABI.number, false),
    TURKEY_BACON_EGG(5,"ターキーベーコンエッグ", 490, Dressings.CAESAR.number, false),
    CHEESE_ROAST_CHICKEN(6,"チーズローストチキン",480, Dressings.NONE.number, false),
    TERIYAKI_CHICKEN(7,"てり焼きチキン", 480, Dressings.MAYONNAISE.number, false),
    CHILI_CHICKEN(8,"チリチキン",410, Dressings.MAYONNAISE.number, false),
    TURKEY(9, "ターキーブレスト",450, Dressings.BALSAMICO.number, false),
    TUNA(10,"ツナ",430, Dressings.PEPPER.number, false),
    EGG(11, "たまご",390, Dressings.SALT_PEPPER.number, false),
    AVOCADO_VEGE(12,"アボカドベジー", 410, Dressings.CREAMY.number, false),
    VEGE_CHEESE(13, "ベジー＆チーズ",340, Dressings.CREAMY.number, false),
    VEGE_DE_LIGHT(14,"ベジーデライト",300, Dressings.OIL_VINEGAR.number, false),
    KINKAKU_BURG(15,"金格バーグ(期間限定)",890, Dressings.WAFU.number, true),
    KINKAKU_DX(16,"金格DX(期間限定)",990, Dressings.WAFU.number, true)
}

enum class Breads(val number: Int, val breadName: String, val price: Int, val limitedFlag :Boolean) {
    WHEAT(0, "ウィート", 0, false),
    WHITE(1, "ホワイト", 0, false),
    SESAME(2,"セサミ",0, false),
    HONEY_OATES(3, "ハニーオーツ",0, false),
    FLAT_BREAD(4, "フラットブレッド", 0, false),
    NONE(5,"無し(サラダ, + 300円)", 300, false),
    ONION_SESAME(6,"オニオンセサミペッパー(期間限定)", 0, true)

}

enum class Toppings(val toppingName: String, val price: Int, val engName: String, val limitedFlag :Boolean) {
    NATURAL_CHEESE("ナチュラルスライスチーズ(+ 40円)", 40, "cheese", false),
    CREAM_CHEESE("クリームタイプチーズ(+ 60円)", 60, "cream", false),
    MASCARPONE_CHEESE("マスカルポーネチーズ(+ 90円)", 90, "mascar", false),
    EGG("たまご(+ 60円)", 60, "egg", false),
    BACON("ベーコン(+ 60円)",60, "bacon", false),
    TUNA("ツナ(+ 80円)",80, "tuna", false),
    SHRIMP("えび(+ 100円)",100, "shrimp", false),
    AVOCADO("アボカド(+ 110円)",110,"avocado", false),
    ROAST_BEEF("ローストビーフ(+ 350円)", 350, "roastbeef", false)
}

enum class Dressings(val number: Int, val dressingName: String, val limitedFlag :Boolean){
    OIL_VINEGAR(0, "オイル&ビネガー　塩・こしょう", false),
    CAESAR(1, "シーザードレッシング", false),
    CREAMY(2,"野菜クリーミードレッシング", false),
    HONEY_MUSTARD(3,"ハニーマスタードソース", false),
    WASABI(4,"わさび醤油ソース", false),
    BASIL(5,"バジルソース", false),
    BALSAMICO(6,"バルサミコソース", false),
    MAYONNAISE(7,"マヨネーズタイプ", false),
    CHILI(8,"チリソース（激辛)", false),
    SALT_PEPPER(9, "塩・こしょう", false),
    PEPPER(10, "こしょう", false),
    NONE(11, "無し", false),
    WAFU(12, "特製和風ソース(期間限定)",true)
}

enum class Amounts(val number: Int, val amount: String){
    NONE(0,"無し"),
    LITTLE(1,"少なめ"),
    NORMAL(2, "普通"),
    MANY(3,"多め"),
    MAX(4,"上限")
}

enum class AmountsDressing(val number: Int, val amount: String){
    LITTLE(0,"少なめ"),
    NORMAL(1, "普通"),
    MANY(2,"多め"),
}

val sandwiches = arrayOf(
        "BLT",
        "えびアボカド",
        "生ハム＆マスカルポーネ",
        "ローストチキン",
        "ローストビーフ　～プレミアム製法～",
        "ターキーベーコンエッグ",
        "チーズローストチキン",
        "てり焼きチキン",
        "チリチキン",
        "ターキーブレスト",
        "ツナ",
        "たまご",
        "アボカドベジー",
        "ベジー＆チーズ",
        "ベジーデライト",
        "金格バーグ(期間限定)",
        "金格DX(期間限定)"
)

val breads = arrayOf(
        "ウィート",
        "ホワイト",
        "セサミ",
        "ハニーオーツ",
        "フラットブレッド",
        "無し(サラダ, + 300円)",
        "オニオンセサミペッパー(期間限定)"
)

val toppings = arrayOf(
        "ナチュラルスライスチーズ(+ 40円)",
        "クリームタイプチーズ(+ 60円)",
        "マスカルポーネチーズ(+ 90円)",
        "たまご(+ 60円)",
        "ベーコン(+ 60円)",
        "ツナ(+ 80円)",
        "えび(+ 100円)",
        "アボカド(+ 110円)",
        "ローストビーフ(+ 350円)"
)

val dressings = arrayOf(
        "オイル&ビネガー　塩・こしょう",
        "シーザードレッシング",
        "野菜クリーミードレッシング",
        "ハニーマスタードソース",
        "わさび醤油ソース",
        "バジルソース",
        "バルサミコソース",
        "マヨネーズタイプ",
        "チリソース（激辛)",
        "塩・こしょう",
        "こしょう",
        "無し",
        "特製和風ソース(期間限定)"
)

val dressingsWoNothing = arrayOf(
        "オイル&ビネガー　塩・こしょう",
        "シーザードレッシング",
        "野菜クリーミードレッシング",
        "ハニーマスタードソース",
        "わさび醤油ソース",
        "バジルソース",
        "バルサミコソース",
        "マヨネーズタイプ",
        "チリソース（激辛)",
        "塩・こしょう",
        "こしょう",
        "特製和風ソース(期間限定)"
)

val amounts = arrayOf(
        "無し",
        "少なめ",
        "普通",
        "多め",
        "上限"
)

val amountsDressing = arrayOf(
        "少なめ",
        "普通",
        "多め"
)