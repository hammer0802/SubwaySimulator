package com.hammer.app.subwaysimulator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.gms.ads.AdRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds



class MainActivity : AppCompatActivity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    private val gson = Gson()
    val list: MutableList<Recipe> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        TutorialActivity.showIfNeeded(this, Bundle())

        Handler().postDelayed({
            val countPreference = getSharedPreferences("countPreference", Context.MODE_PRIVATE)
            var count = countPreference.getInt("count", 0)
            if (count == 0) {
                val sequence = TapTargetSequence(this)
                        .targets(
                                TapTarget.forView(findViewById<View>(R.id.create), "まずはこちらのボタンを押してレシピを作成しましょう！")
                                        .outerCircleColor(R.color.colorPrimary)
                                        .titleTextColor(android.R.color.white)
                                        .drawShadow(true)
                                        .outerCircleAlpha(0.97f)
                                        .cancelable(false)
                                        .tintTarget(false)
                                        .id(1),
                                TapTarget.forToolbarOverflow(toolbar, "操作方法を忘れた場合はこちらをクリック！", "もう一度チュートリアルを見ることができます。")
                                        .outerCircleColor(R.color.colorAccent)
                                        .titleTextColor(android.R.color.white)
                                        .descriptionTextColor(android.R.color.white)
                                        .descriptionTextAlpha(1.0f)
                                        .drawShadow(true)
                                        .outerCircleAlpha(0.97f)
                                        .cancelable(true)
                                        .id(2)
                        )
                sequence.start()
                count++
                val e = countPreference.edit()
                e.putInt("count", count)
                e.apply()
            }
        }, 1000)

        list.clear()
        list.addAll(preference.all.values.filterIsInstance(String::class.java).map { value ->
            gson.fromJson<Recipe>(value, Recipe::class.java)
        }.toMutableList())
        list.sortBy{it.createTime}

        create.setOnClickListener{
            create.isEnabled = false
            val handler = Handler()
            val runnable = Runnable {
                create.isEnabled = true
            }
            handler.postDelayed(runnable, 2000)
            val intent1= Intent(this, CreateRecipeActivity::class.java)
            this.startActivity(intent1)
        }

        MobileAds.initialize(applicationContext, "ca-app-pub-3940256099942544~3347511713")

        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                TutorialActivity.showForcibly(this)
                true}
            R.id.action_policy -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()
        val recyclerAdaptor = MyRecyclerAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = recyclerAdaptor
        recyclerAdaptor.reload()
        recycler_view.adapter!!.notifyDataSetChanged()
    }

}
