package com.hammer.app.subwaysimulator

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.create_recipe.*
import android.widget.LinearLayout



abstract class AbstractRecipeActivity: AppCompatActivity(){
    val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    var sandPrice = 0
    var toppingPrice = 0


    fun spinner(itemName: String, itemArray: Array<String>, spinnerName: String){
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val viewId = resources.getIdentifier(spinnerName, "id", packageName)
        val spinner = findViewById<Spinner>(viewId)
        val checkboxRecommend = findViewById<CheckBox>(R.id.checkBoxRecommend)
        checkboxRecommend.isChecked = true
        // アダプターを設定
        spinner.adapter = adapter
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
        spinner.setSelection(0)
        spinner.setOnTouchListener { _, _ ->
            textViewName.clearFocus()
            false
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        posi: Int, id: Long) {
                val spinner2 = parent as Spinner
                val item = spinner2.selectedItem as String
                if (itemName == "sandwich") {
                    sandPrice = sandPrices[item].toString().toInt()
                    var sum = sandPrice + toppingPrice
                    if(spinnerBread.selectedItem == "無し(サラダ, + ¥300)") sum += 300
                    sumPrice.text = sum.toString()
                    if (checkboxRecommend.isChecked == true){
                        val spinnerDressing = findViewById<Spinner>(R.id.spinnerDressing)
                        spinnerDressing.setSelection(recommendDressing[item].toString().toInt())
                    }
                }
                if(itemName == "bread"){
                    val checkBoxToast = findViewById<CheckBox>(R.id.checkBoxToast)
                    if (spinner.selectedItem == "無し(サラダ, + ¥300)") {
                        checkBoxToast.visibility = View.INVISIBLE
                        val sum = sandPrice + toppingPrice + 300
                        sumPrice.text = sum.toString()
                    } else {
                        checkBoxToast.visibility = View.VISIBLE
                        val sum = sandPrice + toppingPrice
                        sumPrice.text = sum.toString()
                    }
                }
                if (itemName == "dressing") {
                    val spinnerDressingAmount = findViewById<Spinner>(R.id.spinnerDressingAmount)
                    val textViewDressingAmount = findViewById<TextView>(R.id.textViewDressingAmount)
                    if (spinner.selectedItem == "無し") {
                        spinnerDressingAmount.visibility = View.INVISIBLE
                        textViewDressingAmount.visibility = View.INVISIBLE
                    } else {
                        spinnerDressingAmount.visibility = View.VISIBLE
                        textViewDressingAmount.visibility = View.VISIBLE
                    }
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun spinnerDefaultVege(itemArray: Array<String>, spinnerName: String){
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

            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun dressingAmount(){
        val adapterDressingAmount = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, amountsDressing)
        adapterDressingAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerDressingAmount = findViewById<Spinner>(R.id.spinnerDressingAmount)
        spinnerDressingAmount.adapter = adapterDressingAmount
        spinnerDressingAmount.setSelection(1)
        spinnerDressingAmount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        posi: Int, id: Long) {
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    fun checkBox() {
        for (topping in toppings) {
            val viewId = resources.getIdentifier("checkBox" + toppingMap[topping], "id", packageName)
            val checkbox = findViewById<CheckBox>(viewId)
            val counterViewId = resources.getIdentifier("counter" + toppingMap[topping], "id", packageName)
            val counter = findViewById<LinearLayout>(counterViewId)
            checkbox.isChecked = false
            counter.visibility = View.INVISIBLE
            checkbox.text = topping
            checkbox.setOnClickListener {
                toppingPrice = 0
                for (topping2 in toppings) {
                    val viewId2 = resources.getIdentifier("checkBox" + toppingMap[topping2], "id", packageName)
                    val checkbox2 = findViewById<CheckBox>(viewId2)
                    val counterViewId2 = resources.getIdentifier("counter" + toppingMap[topping2], "id", packageName)
                    val counter2 = findViewById<LinearLayout>(counterViewId2)
                    val valueViewId = resources.getIdentifier("value" + toppingMap[topping2], "id", packageName)
                    val value = findViewById<EditText>(valueViewId)
                    if (checkbox2.isChecked) {
                        counter2.visibility = View.VISIBLE
                        toppingPrice += toppingPrices[topping2].toString().toInt()
                    }else{
                        counter2.visibility = View.INVISIBLE
                    }
                }
                var sum = sandPrice + toppingPrice
                if(spinnerBread.selectedItem == "無し(サラダ, + ¥300)") sum += 300
                sumPrice.text = sum.toString()

            } //TextWatcherでトッピング個数をリアルタイムで取得したい
        }
    }

    fun initAddDressingBtn(){
        addDressing.setOnClickListener{
            val selectDressingItemView = LayoutInflater.from(this).inflate(R.layout.select_dressing_item, null, false) as ViewGroup
            selectDressingItemView.id = selectDressingItemView.hashCode()
            select_dressing_container.addView(selectDressingItemView)
        }
    }

override fun onBackPressed() {

        val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                .setTitle("確認")
                .setMessage("作成途中で終了するとレシピは保存されません。"+ "\n" +"終了しますか？")
                .setPositiveButton("はい") { _, _ ->
                    super.onBackPressed()
                }
                .setNegativeButton("キャンセル", null)
                .show()
    }
}

