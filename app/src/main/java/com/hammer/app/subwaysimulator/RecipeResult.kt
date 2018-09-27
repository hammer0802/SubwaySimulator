package com.hammer.app.subwaysimulator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_recipe_result.*

class RecipeResult : AppCompatActivity() {

    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    private val intent2: Intent by lazy { this.intent }
    val key: String by lazy { intent2.getStringExtra("key")}
    val recipe:Recipe by lazy { gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_result)
        setSupportActionBar(toolbar)

        val recipeName = findViewById<TextView>(R.id.recipeName)
        recipeName.append(":"+recipe.name)

        val recipePrice = findViewById<TextView>(R.id.recipePrice)
        recipePrice.append(":"+recipe.price+"円")

        val sandType = findViewById<TextView>(R.id.textViewSandType)
        sandType.text = recipe.sandwich

        val breadType = findViewById<TextView>(R.id.textViewBreadType)
        var toast = ""
        if(recipe.toast){
            toast = "トースト有り"
        }else{
            toast = "トースト無し"
        }
        val breadText = recipe.bread + "(" + toast + ")"
        breadType.text = breadText

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recipe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                true
            }
            R.id.action_sns ->{
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
