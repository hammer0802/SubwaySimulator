package com.hammer.app.subwaysimulator

import android.os.Bundle
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import android.view.WindowId
import android.widget.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hammer.app.subwaysimulator.R.id.*
import kotlinx.android.synthetic.main.create_recipe.*
import android.content.DialogInterface



class CreateRecipe : Activity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    val list: MutableList<Recipe> by lazy { gson?.fromJson<MutableList<Recipe>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Recipe>>() {}.type) ?: mutableListOf()}
    val intent1: Intent by lazy {this.intent}
    val position: Int by lazy { intent1.getIntExtra("position",0) }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_recipe)
        val r = Recipe()


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
                    if(itemName == "sandwich"){
                         r.price = sandPrices[item].toString().toInt()
                    }
                    val e = this@CreateRecipe.preference.edit()
                    when {
                        itemName == "sandwich" -> r.sandwich = item
                        itemName == "bread" -> r.bread = item
                        itemName == "olive" -> r.olive = item
                        itemName == "pickels" -> r.pickles = item
                        itemName == "hotpepper" -> r.hotpepper = item
                        itemName == "dressing" -> r.dressing = item
                    }
                    e.putString("list", gson.toJson(list))
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
                        itemName == "lettuce" -> r.lettuce = item
                        itemName == "tomato" -> r.tomato = item
                        itemName == "greenpepper" -> r.greenpepper = item
                        itemName == "redonion" -> r.redonion = item
                        itemName == "carrot" -> r.carrot = item
                    }
                    e.putString("list", gson.toJson(list))
                }
                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }

        }

        for (topping in toppings){
            val viewId = resources.getIdentifier("checkBox"+ toppingmap[topping], "id", packageName)
            val checkbox = findViewById<CheckBox>(viewId)
            checkbox.isChecked = false
            checkbox.setText(topping)
            checkbox.setOnClickListener(View.OnClickListener{
                if (checkbox.isChecked()) {
                    r.price += toppingPrices[topping].toString().toInt()
                } else {
                    r.price -= toppingPrices[topping].toString().toInt()
                }
            }
            )
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

        val sum = r.price.toString()
        sumPrice.setText(sum)

    }

    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setTitle("確認")
                .setMessage("作成途中で終了するとレシピは保存されません。"+ "\n" +"終了しますか？")
                .setPositiveButton("はい") { dialog, which ->
                    super.onBackPressed()
                }
                .setNegativeButton("キャンセル", null)
                .show()
    }
}

