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
import java.text.SimpleDateFormat
import java.util.*


class CreateRecipe : Activity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    val recipe = Recipe()
    var sandPrice = 0
    var toppingPrice = 0

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
            spinner.setSelection(0)
            spinner.setOnTouchListener { view, motionEvent ->
                textViewName.clearFocus()
                false
            }
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            posi: Int, id: Long) {
                    val spinner2 = parent as Spinner
                    val item = spinner2.selectedItem as String
                    if (itemName == "sandwich") {
                        for (topping in toppings) {
                            sandPrice = sandPrices[item].toString().toInt()
                            val sum = sandPrice + toppingPrice
                            sumPrice.setText(sum.toString())
                        }
                        when {
                            itemName == "sandwich" -> recipe.sandwich = item
                            itemName == "bread" -> recipe.bread = item
                            itemName == "olive" -> recipe.olive = item
                            itemName == "pickels" -> recipe.pickles = item
                            itemName == "hotpepper" -> recipe.hotpepper = item
                            itemName == "dressing" -> recipe.dressing = item
                        }
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
                    val spinner2 = parent as Spinner
                    val item = spinner2.selectedItem as String
                    when {
                        itemName == "lettuce" -> recipe.lettuce = item
                        itemName == "tomato" -> recipe.tomato = item
                        itemName == "greenpepper" -> recipe.greenpepper = item
                        itemName == "redonion" -> recipe.redonion = item
                        itemName == "carrot" -> recipe.carrot = item
                    }
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
                for (topping2 in toppings){
                    val viewId2 = resources.getIdentifier("checkBox"+ toppingmap[topping], "id", packageName)
                    val checkbox2 = findViewById<CheckBox>(viewId2)
                    if (checkbox2.isChecked == true){
                        toppingPrice += toppingPrices[topping2].toString().toInt()
                    }
                }
                val sum = sandPrice + toppingPrice
                sumPrice.setText(sum.toString())
                when {
                    topping == "ナチュラルスライスチーズ(+ ¥40)" -> recipe.cheese = checkbox.isChecked
                    topping == "クリームタイプチーズ(+ ¥60)" -> recipe.cream = checkbox.isChecked
                    topping == "マスカルポーネチーズ(+ ¥90)" -> recipe.mascar = checkbox.isChecked
                    topping == "たまご(+ ¥60)" -> recipe.egg = checkbox.isChecked
                    topping == "ベーコン(+ ¥60)" -> recipe.bacon = checkbox.isChecked
                    topping == "ツナ(+ ¥80)" -> recipe.tuna = checkbox.isChecked
                    topping == "えび(+ ¥100)" -> recipe.shrimp = checkbox.isChecked
                    topping == "アボカド(+ ¥110)" -> recipe.avocado = checkbox.isChecked
                }

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
                            val uuid = UUID.randomUUID().toString()
                            val e = preference!!.edit()
                            recipe.name = textViewName.text.toString()
                            recipe.price = sumPrice.text.toString().toInt()
                            val c = Calendar.getInstance()
                            val sdf = SimpleDateFormat("yyyyMMddHHmmssSSS")
                            recipe.createTime = sdf.format(c.time)
                            e.putString(uuid, gson.toJson(recipe))
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

