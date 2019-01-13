package com.hammer.app.subwaysimulator


//isEnabled == Falseのclassは最後列に移動する

enum class Sandwiches(val sandName: String, val price: Int, val recommendDressing: Int, val isEnabled:Boolean = true){
    BLT("BLT", 420, Dressings.CAESAR.ordinal),
    EBI_AVOCADO("えびアボカド", 500, Dressings.CREAMY.ordinal),
    NAMA_HAM("生ハム＆マスカルポーネ", 520, Dressings.BASIL.ordinal),
    ROAST_CHICKEN("ローストチキン", 420, Dressings.HONEY_MUSTARD.ordinal),
    ROAST_BEEF("ローストビーフ　～プレミアム製法～", 590, Dressings.WASABI.ordinal),
    TURKEY_BACON_EGG("ターキーベーコンエッグ", 490, Dressings.CAESAR.ordinal),
    CHEESE_ROAST_CHICKEN("チーズローストチキン",480, Dressings.NONE.ordinal),
    TERIYAKI_CHICKEN("てり焼きチキン", 480, Dressings.MAYONNAISE.ordinal),
    CHILI_CHICKEN("チリチキン",410, Dressings.MAYONNAISE.ordinal),
    TURKEY("ターキーブレスト",450, Dressings.BALSAMICO.ordinal),
    TUNA("ツナ",430, Dressings.PEPPER.ordinal),
    EGG("たまご",390, Dressings.SALT_PEPPER.ordinal),
    AVOCADO_VEGE("アボカドベジー", 410, Dressings.CREAMY.ordinal),
    VEGE_CHEESE("ベジー＆チーズ",340, Dressings.CREAMY.ordinal),
    VEGE_DE_LIGHT("ベジーデライト",300, Dressings.OIL_VINEGAR.ordinal),
    MOZZARELLA_CHICKEN("大人モッツァレラ・チキン(期間限定)", 490, Dressings.BASIL.ordinal),

    //期間終了
    KINKAKU_BURG("金格バーグ(期間限定)",890, Dressings.WAFU.ordinal, false),
    KINKAKU_DX("金格DX(期間限定)",990, Dressings.WAFU.ordinal, false),
    BACON_CHICKEN_MELT("ベーコンチキンメルト(期間限定)", 510, Dressings.RANCH.ordinal, false),
    CHICKEN_MELT("チキンメルト(期間限定)", 450, Dressings.RANCH.ordinal, false),
}

enum class Breads(val breadName: String, val price: Int = 0, val isEnabled:Boolean = true) {
    WHEAT("ウィート"),
    WHITE("ホワイト"),
    SESAME("セサミ"),
    HONEY_OATES("ハニーオーツ"),
    FLAT_BREAD("フラットブレッド"),
    NONE("無し(サラダ, + 300円)", 300),

    //期間終了
    ONION_SESAME("オニオンセサミペッパー(期間限定)", 0, false),

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

    //期間終了
    SHREDDED_CHEESE("シュレッドチーズ(+ 40円)", 40, "shredded", false),
}

//NONEを最後にしないとエラるのでFalseのclassの直前にする
enum class Dressings(val dressingName: String, val isEnabled:Boolean = true){
    OIL_VINEGAR("オイル&ビネガー　塩・こしょう"),
    CAESAR("シーザードレッシング"),
    CREAMY("野菜クリーミードレッシング"),
    HONEY_MUSTARD("ハニーマスタードソース"),
    WASABI("わさび醤油ソース"),
    BASIL("バジルソース"),
    BALSAMICO("バルサミコソース"),
    MAYONNAISE("マヨネーズタイプ"),
    CHILI("チリソース（激辛)"),
    SALT_PEPPER("塩・こしょう"),
    PEPPER("こしょう"),
    NONE("無し"),

    //期間終了
    WAFU("特製和風ソース(期間限定)", false),
    RANCH("ランチドレッシング(期間限定)", false),

}

enum class Amounts(val amount: String){
    NONE("無し"),
    LITTLE("少なめ"),
    NORMAL("普通"),
    MANY("多め"),
    MAX("上限"),
}

enum class AmountsDressing(val amount: String){
    LITTLE("少なめ"),
    NORMAL("普通"),
    MANY("多め"),
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