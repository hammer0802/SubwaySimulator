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
import android.os.PersistableBundle


class CreateRecipe : Activity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    val list: MutableList<Recipe> by lazy { gson?.fromJson<MutableList<Recipe>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Recipe>>() {}.type) ?: mutableListOf()}
    val intent1: Intent by lazy {this.intent}
    val position: Int by lazy { intent1.getIntExtra("position",0) }
    var sandPrice = 0
    var toppingPrice = 0

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
            spinner.setSelection(0)
            spinner.setOnTouchListener { view, motionEvent ->
                textViewName.clearFocus()
                false
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            posi: Int, id: Long) {
                    val spinner = parent as Spinner
                    val item = spinner.selectedItem as String
                    if (itemName == "sandwich") {
                        for (topping in toppings) {
                            sandPrice = sandPrices[item].toString().toInt()
                            val sum = sandPrice + toppingPrice
                            sumPrice.setText(sum.toString())

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
                        e.apply()
                    }
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
                    e.apply()
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
                toppingPrice = 0
                for (topping in toppings){
                    val viewId2 = resources.getIdentifier("checkBox"+ toppingmap[topping], "id", packageName)
                    val checkbox2 = findViewById<CheckBox>(viewId2)
                    if (checkbox2.isChecked == true){
                        toppingPrice += toppingPrices[topping].toString().toInt()
                    }
                }
                val sum = sandPrice + toppingPrice
                sumPrice.setText(sum.toString())
                val e = this@CreateRecipe.preference.edit()
                when {
                    topping == "ナチュラルスライスチーズ(+ ¥40)" -> r.cheese = checkbox.isChecked
                    topping == "クリームタイプチーズ(+ ¥60)" -> r.cream = checkbox.isChecked
                    topping == "マスカルポーネチーズ(+ ¥90)" -> r.mascar = checkbox.isChecked
                    topping == "たまご(+ ¥60)" -> r.egg = checkbox.isChecked
                    topping == "ベーコン(+ ¥60)" -> r.bacon = checkbox.isChecked
                    topping == "ツナ(+ ¥80)" -> r.tuna = checkbox.isChecked
                    topping == "えび(+ ¥100)" -> r.shrimp = checkbox.isChecked
                    topping == "アボカド(+ ¥110)" -> r.avocado = checkbox.isChecked
                }
                e.putString("list", gson.toJson(list))
                e.apply()
            })
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



        completeButton.setOnClickListener{
            if (textViewName.text.toString().equals("")) {
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setTitle("Caution !")
                        .setMessage("レシピの名前を入力してください")
                        .setNegativeButton("はい", null)
                        .show()
            }else{
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setTitle("確認")
                        .setMessage("レシピを保存しますか？")
                        .setPositiveButton("はい") { dialog, which ->
                            val e = preference!!.edit()
                            r.name = textViewName.text.toString()
                            r.price = sumPrice.text.toString().toInt()
                            list[position] = r
                            e.putString("list", gson.toJson(list))
                            e.apply()
                            finish()
                        }
                        .setNegativeButton("いいえ", null)
                        .show()
            }
        }
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

