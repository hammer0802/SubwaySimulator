package com.hammer.app.subwaysimulator

enum class Sandwiches(val number: Int, val sandName: String, val price: Int, val recommendDressing: Int){
    BLT(0,"BLT", 420, Dressings.CAESAR.number),
    EBI_AVOCADO(1,"えびアボカド", 500, Dressings.CREAMY.number),
    NAMA_HAM(2,"生ハム＆マスカルポーネ", 520, Dressings.BASIL.number),
    ROAST_CHICKEN(3, "ローストチキン", 420, Dressings.HONEY_MUSTARD.number),
    ROAST_BEEF(4,"ローストビーフ　～プレミアム製法～", 590, Dressings.WASABI.number),
    TURKEY_BACON_EGG(5,"ターキーベーコンエッグ", 490, Dressings.CAESAR.number),
    CHEESE_ROAST_CHICKEN(6,"チーズローストチキン",480, Dressings.NONE.number),
    TERIYAKI_CHICKEN(7,"てり焼きチキン", 480, Dressings.MAYONNAISE.number),
    CHILI_CHICKEN(8,"チリチキン",410, Dressings.MAYONNAISE.number),
    TURKEY(9, "ターキーブレスト",450, Dressings.BALSAMICO.number),
    TUNA(10,"ツナ",430, Dressings.PEPPER.number),
    EGG(11, "たまご",390, Dressings.SALT_PEPPER.number),
    AVOCADO_VEGE(12,"アボカドベジー", 410, Dressings.CREAMY.number),
    VEGE_CHEESE(13, "ベジー＆チーズ",340, Dressings.CREAMY.number),
    VEGE_DE_LIGHT(14,"ベジーデライト",300, Dressings.OIL_VINEGAR.number)
}

enum class Breads(val number: Int, val breadName: String, val price: Int) {
    WHEAT(0, "ウィート", 0),
    WHITE(1, "ホワイト", 0),
    SESAME(2,"セサミ",0),
    HONEY_OATES(3, "ハニーオーツ",0),
    FLAT_BREAD(4, "フラットブレッド", 0),
    NONE(5,"無し(サラダ, + 300円)", 300)
}

enum class Toppings(val toppingName: String, val price: Int, val engName: String) {
    NATURAL_CHEESE("ナチュラルスライスチーズ(+ 40円)", 40, "cheese"),
    CREAM_CHEESE("クリームタイプチーズ(+ 60円)", 60, "cream"),
    MASCARPONE_CHEESE("マスカルポーネチーズ(+ 90円)", 90, "mascar"),
    EGG("たまご(+ 60円)", 60, "egg"),
    BACON("ベーコン(+ 60円)",60, "bacon"),
    TUNA("ツナ(+ 80円)",80, "tuna"),
    SHRIMP("えび(+ 100円)",100, "shrimp"),
    AVOCADO("アボカド(+ 110円)",110,"avocado"),
    ROAST_BEEF("ローストビーフ(+ 350円)", 350, "roastbeef")
}

enum class Dressings(val number: Int, val dressingName: String){
    OIL_VINEGAR(0, "オイル&ビネガー　塩・こしょう"),
    CAESAR(1, "シーザードレッシング"),
    CREAMY(2,"野菜クリーミードレッシング"),
    HONEY_MUSTARD(3,"ハニーマスタードソース"),
    WASABI(4,"わさび醤油ソース"),
    BASIL(5,"バジルソース"),
    BALSAMICO(6,"バルサミコソース"),
    MAYONNAISE(7,"マヨネーズタイプ"),
    CHILI(8,"チリソース（激辛)"),
    SALT_PEPPER(9, "塩・こしょう"),
    PEPPER(10, "こしょう"),
    NONE(11, "無し")
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
        "ベジーデライト"
)

val breads = arrayOf(
        "ウィート",
        "ホワイト",
        "セサミ",
        "ハニーオーツ",
        "フラットブレッド",
        "無し(サラダ, + 300円)"
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
        "無し"
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
        "こしょう"
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
