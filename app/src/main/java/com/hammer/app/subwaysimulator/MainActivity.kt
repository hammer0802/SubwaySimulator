package com.hammer.app.subwaysimulator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    val list: MutableList<Recipe> by lazy { gson?.fromJson<MutableList<Recipe>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Recipe>>() {}.type) ?: mutableListOf<Recipe>()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        create.setOnClickListener (android.view.View.OnClickListener {
            val intent1= Intent(this, CreateRecipe::class.java)
            val l= list?.size ?: 0
            intent1.putExtra("position",l)

            val e = preference!!.edit()
            e.putString("list", gson.toJson(list)) //gson.toJson(list)でlistのデータをjson形式で渡す。listは31行目で要素が追加されているため、新しいlistのデータが入る。
            e.commit()
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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()
        var recyclerAdaptor = MyRecyclerAdapter(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = recyclerAdaptor
        recyclerAdaptor.reload()
        recycler_view.adapter!!.notifyDataSetChanged()
    }

}
