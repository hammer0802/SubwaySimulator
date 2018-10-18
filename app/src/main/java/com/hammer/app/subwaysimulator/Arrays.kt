package com.hammer.app.subwaysimulator

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
        "アボカドチキン　チポトレ(期間限定)",
        "アボカドベーコン　チポトレ(期間限定)")

val sandPrices = mapOf(
        "BLT" to 420,
        "えびアボカド" to 500,
        "生ハム＆マスカルポーネ" to 520,
        "ローストチキン" to 420,
        "ローストビーフ　～プレミアム製法～" to 590,
        "ターキーベーコンエッグ" to 490,
        "チーズローストチキン" to 480,
        "てり焼きチキン" to 480,
        "チリチキン" to 410,
        "ターキーブレスト" to 450,
        "ツナ" to 430,
        "たまご" to 390,
        "アボカドベジー" to 410,
        "ベジー＆チーズ" to 340,
        "ベジーデライト" to 300,
        "アボカドチキン　チポトレ(期間限定)" to 530,
        "アボカドベーコン　チポトレ(期間限定)" to 530
)

val breads = arrayOf(
        "ウィート",
        "ホワイト",
        "セサミ",
        "ハニーオーツ",
        "フラットブレッド",
        "無し(サラダ, + ¥300)"
)

val toppings = arrayOf(
        "ナチュラルスライスチーズ(+ ¥40)",
        "クリームタイプチーズ(+ ¥60)",
        "マスカルポーネチーズ(+ ¥90)",
        "たまご(+ ¥60)",
        "ベーコン(+ ¥60)",
        "ツナ(+ ¥80)",
        "えび(+ ¥100)",
        "アボカド(+ ¥110)",
        "ローストビーフ(+ ¥340)"
)

val toppingPrices = mapOf(
        "ナチュラルスライスチーズ(+ ¥40)" to 40,
        "クリームタイプチーズ(+ ¥60)" to 60,
        "マスカルポーネチーズ(+ ¥90)" to 90,
        "たまご(+ ¥60)" to 60,
        "ベーコン(+ ¥60)" to 60,
        "ツナ(+ ¥80)" to 80,
        "えび(+ ¥100)" to 100,
        "アボカド(+ ¥110)" to 110,
        "ローストビーフ(+ ¥340)" to 340
)

val toppingMap = mapOf(
        "ナチュラルスライスチーズ(+ ¥40)" to "cheese",
        "クリームタイプチーズ(+ ¥60)" to "cream",
        "マスカルポーネチーズ(+ ¥90)" to "mascar",
        "たまご(+ ¥60)" to "egg",
        "ベーコン(+ ¥60)" to "bacon",
        "ツナ(+ ¥80)" to "tuna",
        "えび(+ ¥100)" to "shrimp",
        "アボカド(+ ¥110)" to "avocado",
        "ローストビーフ(+ ¥340)" to "roastbeef"
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
        "チポトレ　サウスウェストソース(期間限定)",
        "無し"
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

val recommendDressing = mapOf(
        "BLT" to 1, //"シーザードレッシング"
        "えびアボカド" to 2, //"野菜クリーミードレッシング"
        "生ハム＆マスカルポーネ" to 5, //"バジルソース"
        "ローストチキン" to 3, //"ハニーマスタードソース"
        "ローストビーフ　～プレミアム製法～" to 4, //"わさび醤油ソース"
        "ターキーベーコンエッグ" to 1, //"シーザードレッシング"
        "チーズローストチキン" to 12, //"無し"
        "てり焼きチキン" to 7, //"マヨネーズタイプ"
        "チリチキン" to 7, //"マヨネーズタイプ"
        "ターキーブレスト" to 6, //"バルサミコソース"
        "ツナ" to 10, //"こしょう"
        "たまご" to 9, //"塩・こしょう"
        "アボカドベジー" to 2, //"野菜クリーミードレッシング"
        "ベジー＆チーズ" to 2, //"野菜クリーミードレッシング"
        "ベジーデライト" to 0, //"オイル&ビネガー　塩・こしょう"
        "アボカドチキン　チポトレ(期間限定)" to 11, //"チポトレ　サウスウェストソース(期間限定)"
        "アボカドベーコン　チポトレ(期間限定)" to 11 //"チポトレ　サウスウェストソース(期間限定)"
)
