package com.hammer.app.subwaysimulator



var sandwiches = arrayOf(
        "BLT", 
        "えびアボカド",
        "生ハム＆マスカルポーネ",
        "ローストチキン",
        "ローストビーフ　～プレミアム製法～",
        "サブウェイクラブ",
        "ターキーベーコンエッグ",
        "チーズローストチキン",
        "炭火てり焼きチキン",
        "ターキーブレスト",
        "ツナ",
        "ハム",
        "たまご",
        "アボカドベジー",
        "ベジー＆チーズ",
        "ベジーデライト",
        "直火焼きタンドリーチキン(期間限定)",
        "スパイシーシュリンプ(期間限定)")

var breads = arrayOf(
    "ウィート",
    "ホワイト",
    "セサミ",
    "ハニーオーツ",
    "フラットブレッド"
)

var toppings = arrayOf(
    "ナチュラルスライスチーズ",
    "クリームタイプチーズ",
    "マスカルポーネチーズ",
    "たまご",
    "ベーコン",
    "ツナ",
    "えび",
    "アボカド"
)

var vegetables = arrayOf(
    "レタス",
    "トマト",
    "ピーマン",
    "レッドオニオン",
    "ニンジン",
    "オリーブ",
    "ピクルス",
    "ホットペッパー"
)

var dressings = arrayOf(
    "オイル&ビネガー　塩・こしょう",
    "シーザードレッシング",
    "野菜クリーミードレッシング",
    "ハニーマスタードソース",
    "わさび醤油ソース",
    "バジルソース",
    "バルサミコソース",
    "マヨネーズタイプ",
    "チリソース（激辛)"
)

var amounts = arrayOf(
    "無し",
    "少なめ",
    "普通",
    "多め"
)

enum class Amount {
    no,
    small,
    middle,
    large
}