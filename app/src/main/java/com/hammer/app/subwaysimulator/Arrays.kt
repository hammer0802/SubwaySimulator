package com.hammer.app.subwaysimulator

enum class sandwiches(val number: Int, val sandName: String, val price: Int, val recommendDressing: Int){
    BLT(0,"BLT", 420, dressings.CAESAR.number),
    EBI_AVOCADO(1,"えびアボカド", 500, dressings.CREAMY.number),
    NAMA_HAM(2,"生ハム＆マスカルポーネ", 520, dressings.BASIL.number),
    ROAST_CHICKEN(3, "ローストチキン", 420, dressings.HONEY_MUSTARD.number),
    ROAST_BEEF(4,"ローストビーフ　～プレミアム製法～", 590, dressings.WASABI.number),
    TURKEY_BACON_EGG(5,"ターキーベーコンエッグ", 490, dressings.CAESAR.number),
    CHEESE_ROAST_CHICKEN(6,"チーズローストチキン",480, dressings.NONE.number),
    TERIYAKI_CHICKEN(7,"てり焼きチキン", 480, dressings.MAYONNAISE.number),
    CHILI_CHICKEN(8,"チリチキン",410, dressings.MAYONNAISE.number),
    TURKEY(9, "ターキーブレスト",450, dressings.BALSAMICO.number),
    TUNA(10,"ツナ",430, dressings.PEPPER.number),
    EGG(11, "たまご",390, dressings.SALT_PEPPER.number),
    AVOCADO_VEGE(12,"アボカドベジー", 410, dressings.CREAMY.number),
    VEGE_CHEESE(13, "ベジー＆チーズ",340, dressings.CREAMY.number),
    VEGE_DE_LIGHT(14,"ベジーデライト",300, dressings.OIL_VINEGAR.number)
}

enum class breads(val number: Int, val breadName: String, val price: Int) {
    WHEAT(0, "ウィート", 0),
    WHITE(1, "ホワイト", 0),
    SESAME(2,"セサミ",0),
    HONEY_OATES(3, "ハニーオーツ",0),
    FLAT_BREAD(4, "フラットブレッド", 0),
    NONE(5,"無し(サラダ, + 300円)", 300)
}

enum class toppings(val toppingName: String, val price: Int, val engName: String) {
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

enum class dressings(val number: Int, val dressingName: String){
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
