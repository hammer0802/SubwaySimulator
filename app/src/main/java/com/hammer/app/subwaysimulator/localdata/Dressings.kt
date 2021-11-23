package com.hammer.app.subwaysimulator.localdata

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

val dressings = Dressings.values().filter { it.isEnabled }.map { it.dressingName }.toTypedArray()

val dressingsWoNothing = Dressings.values().filter { it.isEnabled && it != Dressings.NONE }.map { it.dressingName }.toTypedArray()