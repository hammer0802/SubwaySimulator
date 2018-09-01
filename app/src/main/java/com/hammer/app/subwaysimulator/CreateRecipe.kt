package com.hammer.app.subwaysimulator

import com.hammer.app.subwaysimulator.R.id.spinnerSand
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.WindowId
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hammer.app.subwaysimulator.R.id.item_touch_helper_previous_elevation
import kotlinx.android.synthetic.main.create_recipe.*

class CreateRecipe : Activity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    val list: MutableList<Recipe> by lazy { gson?.fromJson<MutableList<Recipe>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Recipe>>() {}.type) ?: mutableListOf()}
    val intent1: Intent by lazy {this.intent}
    val position: Int by lazy { intent1.getIntExtra("position",0) }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_recipe)


        fun spinner(itemName: String, itemArray: Array<String>, spinnerName: String){
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val viewId = resources.getIdentifier(spinnerName, "id", packageName)
            val spinner = findViewById<Spinner>(viewId)
            // アダプターを設定
            spinner.adapter = adapter
            // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
            spinner.setOnTouchListener { view, motionEvent ->
                textViewName.clearFocus()
                false
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            posi: Int, id: Long) {
                    val spinner = parent as Spinner
                    val item = spinner.selectedItem as String
                    val e = this@CreateRecipe.preference.edit()
                    when {
                        itemName == "sandwich" -> list[position].sandwich = item
                        itemName == "bread" -> list[position].bread = item
                        itemName == "olive" -> list[position].olive = item
                        itemName == "pickels" -> list[position].pickles = item
                        itemName == "hotpepper" -> list[position].hotpepper = item
                        itemName == "dressing" -> list[position].dressing = item
                    }
                    e.putString("list", gson.toJson(list))
                    e.apply()
                }

                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }
        }

        fun spinnerDefaultVege(itemName: String, itemArray: Array<String>, spinnerName: String){
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val viewId = resources.getIdentifier(spinnerName, "id", packageName)
            val spinner = findViewById<Spinner>(viewId)

            // アダプターを設定
            spinner.adapter = adapter
            spinner.setSelection(2)
            // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            posi: Int, id: Long) {
                    val spinner = parent as Spinner
                    val item = spinner.selectedItem as String
                    val e = this@CreateRecipe.preference.edit()
                    when {
                        itemName == "lettuce" -> list[position].lettuce = item
                        itemName == "tomato" -> list[position].tomato = item
                        itemName == "greenpepper" -> list[position].greenpepper = item
                        itemName == "redonion" -> list[position].redonion = item
                        itemName == "carrot" -> list[position].carrot = item
                    }
                    e.putString("list", gson.toJson(list))
                    e.apply()
                    textViewName.clearFocus()
                }
                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }

        }

        for (topping in toppings){
            val viewId = resources.getIdentifier("checkBox"+ toppingmap[topping], "id", packageName)
            val checkbox = findViewById<CheckBox>(viewId)
            checkbox.isChecked = false
            checkbox.setText(topping)
        }

        spinner("sandwich", sandwiches, "spinnerSand")
        spinner("bread", breads, "spinnerBread")
        spinnerDefaultVege("lettuce", amounts, "spinnerLettuce")
        spinnerDefaultVege("tomato", amounts, "spinnerTomato")
        spinnerDefaultVege("greenpepper", amounts, "spinnerGreenpepper")
        spinnerDefaultVege("redonion", amounts, "spinnerRedonion")
        spinnerDefaultVege("carrot", amounts, "spinnerCarrot")
        spinner("pickles", amounts, "spinnerPickles")
        spinner("olive", amounts, "spinnerOlive")
        spinner("hotpepper", amounts, "spinnerHotpepper")
        spinner("dressings", dressings, "spinnerDressing")


    }

    override fun onBackPressed() {
        super.onBackPressed()
        list.removeAt(index = position)

    }
}

