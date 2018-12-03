package com.hammer.app.subwaysimulator


//isEnabled == Falseのclassは最後列に移動する

enum class Sandwiches(val number: Int, val sandName: String, val price: Int, val recommendDressing: Int, val isEnabled:Boolean = true){
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
    VEGE_DE_LIGHT(14,"ベジーデライト",300, Dressings.OIL_VINEGAR.number),
    KINKAKU_BURG(15,"金格バーグ(期間限定)",890, Dressings.WAFU.number),
    KINKAKU_DX(16,"金格DX(期間限定)",990, Dressings.WAFU.number),
}

enum class Breads(val number: Int, val breadName: String, val price: Int = 0, val isEnabled:Boolean = true) {
    WHEAT(0, "ウィート"),
    WHITE(1, "ホワイト"),
    SESAME(2,"セサミ"),
    HONEY_OATES(3, "ハニーオーツ"),
    FLAT_BREAD(4, "フラットブレッド"),
    NONE(5,"無し(サラダ, + 300円)", 300),
    ONION_SESAME(6,"オニオンセサミペッパー(期間限定)",0),

}

enum class Toppings(val toppingName: String, val price: Int, val engName: String, val isEnabled:Boolean = true) {
    NATURAL_CHEESE("ナチュラルスライスチーズ(+ 40円)", 40, "cheese"),
    CREAM_CHEESE("クリームタイプチーズ(+ 60円)", 60, "cream"),
    MASCARPONE_CHEESE("マスカルポーネチーズ(+ 90円)", 90, "mascar"),
    EGG("たまご(+ 60円)", 60, "egg"),
    BACON("ベーコン(+ 60円)",60, "bacon"),
    TUNA("ツナ(+ 80円)",80, "tuna"),
    SHRIMP("えび(+ 100円)",100, "shrimp"),
    AVOCADO("アボカド(+ 110円)",110,"avocado"),
    ROAST_BEEF("ローストビーフ(+ 350円)", 350, "roastbeef"),
}

//NONEを最後にしないとエラるのでFalseのclassの直前にする
enum class Dressings(val number: Int, val dressingName: String, val isEnabled:Boolean = true){
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
    WAFU(11, "特製和風ソース(期間限定)"),
    NONE(12, "無し"),

}

enum class Amounts(val number: Int, val amount: String){
    NONE(0,"無し"),
    LITTLE(1,"少なめ"),
    NORMAL(2, "普通"),
    MANY(3,"多め"),
    MAX(4,"上限"),
}

enum class AmountsDressing(val number: Int, val amount: String){
    LITTLE(0,"少なめ"),
    NORMAL(1, "普通"),
    MANY(2,"多め"),
}

enum class FootLong(val price: Int){
    FOOT_LONG(300)
}

val sandwiches = Sandwiches.values().filter { it.isEnabled }.map { it.sandName }.toTypedArray()

val breads = Breads.values().filter { it.isEnabled }.map { it.breadName }.toTypedArray()

val toppings = Toppings.values().filter { it.isEnabled }.map { it.toppingName }.toTypedArray()

val dressings = Dressings.values().filter { it.isEnabled }.map { it.dressingName }.toTypedArray()

val dressingsWoNothing = Dressings.values().filter { it.isEnabled && it != Dressings.NONE }.map { it.dressingName }.toTypedArray()

val amounts = Amounts.values().map { it.amount }.toTypedArray()

val amountsDressing = AmountsDressing.values().map { it.amount }.toTypedArray()