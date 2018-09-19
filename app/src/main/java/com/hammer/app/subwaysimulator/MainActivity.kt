package com.hammer.app.subwaysimulator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hammer.app.subwaysimulator.R.attr.key

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    private val gson = Gson()
    val list: MutableList<Recipe> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        TutorialActivity.showIfNeeded(this, Bundle())

        TapTargetSequence(this)
                .target(
                        TapTarget.forView(findViewById<Button>(R.id.create), "まずはこちらのボタンを押してレシピを作成しましょう！")
                                .outerCircleColor(R.color.colorPrimary)
                                .titleTextColor(android.R.color.white)
                                .drawShadow(true)
                                .outerCircleAlpha(0.95f)
                                .cancelable(true)
                )

        val allKeys = preference.all.keys
        for (key in allKeys){
            list.add(gson.fromJson<Recipe>(preference!!.getString(key, ""), Recipe::class.java))
        }
        list.sortBy{it.createTime}

        create.setOnClickListener (android.view.View.OnClickListener {
            val intent1= Intent(this, CreateRecipe::class.java)
//            val l= list?.size ?: 0
//            intent1.putExtra("position",l)
//
//            val e = preference!!.edit()
//            e.putString(l.toString(), "")
//            e.apply()
            this.startActivity(intent1)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                TutorialActivity.showForcibly(this)
                true}
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
