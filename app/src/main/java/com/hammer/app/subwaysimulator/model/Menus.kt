package com.hammer.app.subwaysimulator.model

class Sandwich(val name :String, val price :Int, val recoommendDressing :String, val isEnabled :Boolean = true){
    companion object {
        fun createMenus() = listOf(
                Sandwich("BLT", 420, "野菜クリーミードレッシング"),
                Sandwich("えびアボカド", 500, "野菜クリーミードレッシング"),
                Sandwich("生ハム＆マスカルポーネ", 520, "バジルソース"),
                Sandwich("ローストチキン", 420, "ハニーマスタードソース"),
                Sandwich("ローストビーフ　～プレミアム製法～", 590, "わさび醤油ソース"),
                Sandwich("ターキーベーコンエッグ", 490, "シーザードレッシング"),
                Sandwich("チーズローストチキン",480, "無し"),
                Sandwich("てり焼きチキン", 480,"マヨネーズタイプ" ),
                Sandwich("チリチキン",410, "マヨネーズタイプ"),
                Sandwich("ターキーブレスト",450, "バルサミコソース"),
                Sandwich("ツナ",430,"こしょう"),
                Sandwich("たまご",390, "塩・こしょう"),
                Sandwich("アボカドベジー", 410,"野菜クリーミードレッシング"),
                Sandwich("ベジー＆チーズ",340,"野菜クリーミードレッシング"),
                Sandwich("ベジーデライト",300, "オイル&ビネガー　塩・こしょう"),
                Sandwich("大人モッツァレラ・チキン(期間限定)", 490, "バジルソース"),

                //期間終了
                Sandwich("金格バーグ(期間限定)",890, "特製和風ソース(期間限定)",false),
                Sandwich("金格DX(期間限定)",990,"特製和風ソース(期間限定)",false),
                Sandwich("ベーコンチキンメルト(期間限定)", 510, "ランチドレッシング(期間限定)", false),
                Sandwich("チキンメルト(期間限定)", 450, "ランチドレッシング(期間限定)",false)
        )
    }
}


class Bread(val name :String, val price :Int = 0, val isEnabled :Boolean = true){
    companion object {
        fun createBreads() = listOf(
                Bread("ウィート"),
                Bread("ホワイト"),
                Bread("セサミ"),
                Bread("ハニーオーツ"),
                Bread("フラットブレッド"),
                Bread("無し(サラダ, + 300円)", 300),

                //期間終了
                Bread("オニオンセサミペッパー(期間限定)", 0, false)
        )
    }
}

class Topping(val name: String, val price: Int, val engName: String, val isEnabled:Boolean = true){
    companion object {
        fun createToppings() = listOf(
                Topping("ナチュラルスライスチーズ(+ 40円)", 40, "cheese"),
                Topping("クリームタイプチーズ(+ 60円)", 60, "cream"),
                Topping("マスカルポーネチーズ(+ 90円)", 90, "mascar"),
                Topping("たまご(+ 60円)", 60, "egg"),
                Topping("ベーコン(+ 60円)",60, "bacon"),
                Topping("ツナ(+ 80円)",80, "tuna"),
                Topping("えび(+ 100円)",100, "shrimp"),
                Topping("アボカド(+ 110円)",110,"avocado"),
                Topping("ローストビーフ(+ 350円)", 350, "roastbeef"),

                //期間終了
                Topping("シュレッドチーズ(+ 40円)", 40, "shredded", false)
        )
    }
}

class Vegetable(val name :String, val isEnabled :Boolean = true){
    companion object {
        fun createVegetables() = listOf(
                Vegetable("レタス"),
                Vegetable("トマト"),
                Vegetable("ピーマン"),
                Vegetable("オニオン"),
                Vegetable("ニンジン"),
                Vegetable("オリーブ"),
                Vegetable("ピクルス"),
                Vegetable("ホットペッパー"),
                //期間終了
                Vegetable("レッドオニオン",false)
        )
    }
}

class Dressing(val name :String, val isEnabled :Boolean = true){
    companion object {
        fun createDressings() = listOf(
                Dressing("オイル&ビネガー　塩・こしょう"),
                Dressing("シーザードレッシング"),
                Dressing("野菜クリーミードレッシング"),
                Dressing("ハニーマスタードソース"),
                Dressing("わさび醤油ソース"),
                Dressing("バジルソース"),
                Dressing("バルサミコソース"),
                Dressing("マヨネーズタイプ"),
                Dressing("チリソース（激辛)"),
                Dressing("塩・こしょう"),
                Dressing("こしょう"),

                //期間終了
                Dressing("特製和風ソース(期間限定)", false),
                Dressing("ランチドレッシング(期間限定)", false)
        )
    }
}