package com.hammer.app.subwaysimulator.ui

import android.app.Activity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.hammer.app.subwaysimulator.R
import com.stephentuso.welcome.*

class TutorialActivity : WelcomeActivity() {
    companion object {
        /**
         * まだ表示していなかったらチュートリアルを表示
         * SharedPreferencesの管理に関しては内部でよしなにやってくれているので普通に呼ぶだけで良い
         */
        fun showIfNeeded(activity: Activity, savedInstanceState: Bundle?) {
            WelcomeHelper(activity, TutorialActivity::class.java).show(savedInstanceState)
        }

        /**
         * 強制的にチュートリアルを表示したい時にはこちらを呼ぶ
         */
        fun showForcibly(activity: Activity) {
            WelcomeHelper(activity, TutorialActivity::class.java).forceShow()
        }
    }

    /**
     * 表示するチュートリアル画面を定義する
     */
    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(BackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)))
                .page(TitlePage(R.drawable.icon, "SUBWAYシミュレーターへ"+ "\n" +"ようこそ！"))
                .page(BasicPage(
                    R.drawable.capture1,
                        "お気に入りを忘れないように", "このアプリではSUBWAYのサンドウィッチ・レシピを作成・保存することができます")
                        .background(BackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))))
                .page(BasicPage(
                    R.drawable.capture2,
                        "あなただけのレシピを作りましょう","SUBWAYの裏技完全対応！タップするだけでオリジナルレシピが作れます")
                        .background(BackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))))
                .page(BasicPage(
                    R.drawable.capture3,
                        "レシピを作ったら","作ったレシピは編集したり、画像として保存や友達へ共有もできます")
                        .background(BackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))))
                .page(BasicPage(
                    R.drawable.capture4,
                        "レシピを削除したい場合は","レシピリストを長押しすることで削除できます")
                        .background(BackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))))
                .swipeToDismiss(true)
                .build()
    }

}
