package com.hammer.app.subwaysimulator

import android.widget.AdapterView
import android.widget.Toast
import android.widget.Spinner
import com.hammer.app.subwaysimulator.R.id.spinnerSand
import android.widget.ArrayAdapter
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.WindowId
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.create_recipe.*

class CreateRecipe : Activity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("kazu", Context.MODE_PRIVATE) }
    val gson = Gson()
    val list: MutableList<Recipe> by lazy { gson?.fromJson<MutableList<Recipe>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Recipe>>() {}.type) ?: mutableListOf<Recipe>()}
    val intent1: Intent by lazy {this.intent}
    val position: Int by lazy { intent1.getIntExtra("position",0) }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_recipe)

        fun spinner(itemArray: Array<String>, spinnerName: String){
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val viewId = resources.getIdentifier(spinnerName, "id", packageName)
            val spinner = findViewById<Spinner>(viewId)
            // アダプターを設定
            spinner.adapter = adapter
            // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            position: Int, id: Long) {
                    val spinner = parent as Spinner

                }

                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }
        }

        spinner(sandwiches, "spinnerSand")
        spinner(breads, "spinnerBread")
    }
}

