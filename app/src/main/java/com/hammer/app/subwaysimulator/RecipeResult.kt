package com.hammer.app.subwaysimulator

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeResult : Activity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    val recipe: Recipe by lazy { gson?.fromJson<Recipe>(preference!!.getString(position.toString(), "0"), object : TypeToken<Recipe>() {}.type) ?: Recipe()}
    val intent2: Intent by lazy {this.intent}
    val position: Int by lazy { intent2.getIntExtra("position",0) }

}
