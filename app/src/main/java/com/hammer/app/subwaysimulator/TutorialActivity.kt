package com.hammer.app.subwaysimulator

import android.app.Activity
import android.net.IpSecTransform
import android.os.Bundle
import android.support.v4.content.ContextCompat
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
                        "お気に入りのサンドウィッチを忘れないように…",
                        "このアプリではSUBWAYのサンドウィッチ・レシピを作成・保存することができます。")
                        .background(BackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))))
                .page(BasicPage(
                        R.drawable.capture2,
                        "あなたのご要望を叶えます",
                        "野菜の増減やパン無し・ドレッシング無しなどの細かいご指定も、タップするだけで簡単にできます。")
                        .background(BackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))))
                .page(BasicPage(
                        R.drawable.capture3,
                        "あなたのお気に入りレシピを友人に教えてあげましょう！",
                        "SNS連携機能を使って、作成したレシピを共有することができます。")
                        .background(BackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary)))
                )
                .swipeToDismiss(true)
                .build()
    }

}
