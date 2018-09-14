package com.hammer.app.subwaysimulator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.TestLooperManager
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.recipe_result.*

class RecipeResult : Activity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    val intent2: Intent by lazy {this.intent}
    val position: Int by lazy { intent2.getIntExtra("position",0) }
    val key: String by lazy { intent2.getStringExtra("key") }
    val recipe: Recipe by lazy { gson?.fromJson<Recipe>(preference!!.getString(key, ""), Recipe::class.java) ?: Recipe()}

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_result)




    }
}
