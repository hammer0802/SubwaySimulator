package com.hammer.app.subwaysimulator

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.create_recipe.*
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.select_dressing_item.*
import android.text.InputFilter
import android.widget.TextView
import com.hammer.app.subwaysimulator.model.*

abstract class AbstractRecipeActivity: AppCompatActivity(){
    val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    var sandPrice = 0
    var toppingPrice = 0
    var addDressingCount = 0
    var addDressing2Count = 0

    protected fun spinner(itemName: String, itemArray: Array<String>, spinnerName: String){
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val viewId = resources.getIdentifier(spinnerName, "id", packageName)
        val spinner = findViewById<Spinner>(viewId)
        val checkboxRecommend = findViewById<CheckBox>(R.id.checkBoxRecommend)
        val checkBoxFootLong = findViewById<CheckBox>(R.id.checkBoxFootLong)
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
                    val selectedSand = Sandwich.values().single{ it.sandName == item }
                    sandPrice = selectedSand.price
                    var sum = sandPrice + toppingPrice
                    if(checkBoxFootLong.isChecked) sum += FootLong.FOOT_LONG.price
                    if(spinnerBread.selectedItem == Bread.NONE.breadName) sum += Bread.NONE.price
                    sumPrice.text = sum.toString()
                    if (checkboxRecommend.isChecked){
                        spinnerDressing.setSelection(selectedSand.recommendDressing.ordinal)

                    }
                }
                if(itemName == "bread"){
                    val checkBoxToast = findViewById<CheckBox>(R.id.checkBoxToast)
                    if (spinner.selectedItem == Bread.NONE.breadName) {
                        checkBoxToast.visibility = View.GONE
                        checkBoxFootLong.visibility = View.GONE
                        val sum = sandPrice + toppingPrice + Bread.NONE.price
                        sumPrice.text = sum.toString()
                    } else {
                        checkBoxToast.visibility = View.VISIBLE
                        checkBoxFootLong.visibility = View.VISIBLE
                        var sum = sandPrice + toppingPrice
                        if (checkBoxFootLong.isChecked) sum += FootLong.FOOT_LONG.price
                        sumPrice.text = sum.toString()
                    }
                }
                if (itemName == "dressing") {
                    val spinnerDressingAmount = findViewById<Spinner>(R.id.spinnerDressingAmount)
                    val textViewDressingAmount = findViewById<TextView>(R.id.textViewDressingAmount)
                    if (spinner.selectedItem == Dressing.NONE.dressingName) {
                        spinnerDressingAmount.visibility = View.GONE
                        textViewDressingAmount.visibility = View.GONE
                        if (addDressingCount == 1){
                            addDressing2.visibility = View.GONE
                            addDressingText2.visibility = View.GONE
                            spinnerDressing2.visibility = View.GONE
                            textViewDressingType2.visibility = View.GONE
                            spinnerDressingAmount2.visibility = View.GONE
                            textViewDressingAmount2.visibility = View.GONE
                            howToDress.visibility = View.GONE
                        }else{
                            addDressing.visibility = View.GONE
                            addDressingText.visibility = View.GONE
                        }
                    } else {
                        spinnerDressingAmount.visibility = View.VISIBLE
                        textViewDressingAmount.visibility = View.VISIBLE
                        if(addDressingCount == 1 && removeDressing.visibility == View.VISIBLE){
                            spinnerDressing2.visibility = View.VISIBLE
                            textViewDressingType2.visibility = View.VISIBLE
                            spinnerDressingAmount2.visibility = View.VISIBLE
                            textViewDressingAmount2.visibility = View.VISIBLE
                            howToDress.visibility = View.VISIBLE
                        }else if(addDressingCount == 1){
                            addDressing2.visibility = View.VISIBLE
                            addDressingText2.visibility = View.VISIBLE
                        }else if(addDressing2Count == 0 ){
                            addDressing.visibility = View.VISIBLE
                            addDressingText.visibility = View.VISIBLE
                        }
                    }
                    val selectedSand =  Sandwich.values().single { spinnerSand.selectedItem == it.sandName }
                    if(checkboxRecommend.isChecked && spinner.selectedItem != selectedSand.recommendDressing.dressingName) checkboxRecommend.isChecked = false
                }
            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    protected fun spinnerDefaultVege(itemArray: Array<String>, spinnerName: String){
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val viewId = resources.getIdentifier(spinnerName, "id", packageName)
        val spinner = findViewById<Spinner>(viewId)
        // アダプターを設定
        spinner.adapter = adapter
        spinner.setSelection(2)
        spinner.setOnTouchListener { _, _ ->
            textViewName.clearFocus()
            false
        }
        // スピナーのアイテムが選択された時に呼び出されるコールバックリスナーを登録
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        posi: Int, id: Long) {

            }
            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    protected fun dressingAmount(){
        val adapterDressingAmount = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, amountsDressing)
        adapterDressingAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerDressingAmount = findViewById<Spinner>(R.id.spinnerDressingAmount)
        spinnerDressingAmount.adapter = adapterDressingAmount
        spinnerDressingAmount.setSelection(1)
        spinnerDressingAmount.setOnTouchListener { _, _ ->
            textViewName.clearFocus()
            false
        }
        spinnerDressingAmount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        posi: Int, id: Long) {
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    protected fun checkBox() {
        toppings.forEach { topping ->
            val toppingName = Topping.values().single { tpp -> tpp.toppingName == topping }
            val viewId = resources.getIdentifier("checkBox${toppingName.engName}", "id", packageName)
            val checkbox = findViewById<CheckBox>(viewId)
            val counterViewId = resources.getIdentifier("counter${toppingName.engName}", "id", packageName)
            val counter = findViewById<LinearLayout>(counterViewId)
            val checkBoxFootLong = findViewById<CheckBox>(R.id.checkBoxFootLong)
            checkbox.isChecked = false
            counter.visibility = View.INVISIBLE
            checkbox.text = topping
            checkbox.setOnClickListener {
                textViewName.clearFocus()
                toppingPrice = 0
                toppings.forEach { topping2 ->
                    val toppingName2 = Topping.values().single { tpp2 -> tpp2.toppingName == topping2 }
                    val viewId2 = resources.getIdentifier("checkBox${toppingName2.engName}", "id", packageName)
                    val checkbox2 = findViewById<CheckBox>(viewId2)
                    val counterViewId2 = resources.getIdentifier("counter${toppingName2.engName}", "id", packageName)
                    val counter2 = findViewById<LinearLayout>(counterViewId2)
                    val valueViewId = resources.getIdentifier("value${toppingName2.engName}", "id", packageName)
                    val valueTextView = findViewById<TextView>(valueViewId)
                    if (checkbox2.isChecked) {
                        counter2.visibility = View.VISIBLE
                        toppingPrice += toppingName2.price * valueTextView.text.toString().toInt()
                    }else{
                        counter2.visibility = View.INVISIBLE
                    }
                }
                var sum = sandPrice + toppingPrice
                if(spinnerBread.selectedItem == Bread.NONE.breadName) sum += Bread.NONE.price
                if(checkBoxFootLong.isChecked) sum += FootLong.FOOT_LONG.price
                sumPrice.text = sum.toString()
            }
        }
    }

    protected fun counterBtn(){
        toppings.forEach{ topping ->
            val toppingName = Topping.values().single { tpp -> tpp.toppingName == topping }
            val upBtnViewId = resources.getIdentifier("up${toppingName.engName}", "id", packageName)
            val upBtn = findViewById<ImageButton>(upBtnViewId)
            val valueViewId = resources.getIdentifier("value${toppingName.engName}", "id", packageName)
            val valueTextView = findViewById<TextView>(valueViewId)
            val downBtnViewId = resources.getIdentifier("down${toppingName.engName}", "id", packageName)
            val downBtn = findViewById<ImageButton>(downBtnViewId)
            val checkBoxFootLong = findViewById<CheckBox>(R.id.checkBoxFootLong)
            valueTextView.filters = arrayOf<InputFilter>(MinMaxFilter("1", "9"))
            var v = valueTextView.text.toString().toInt()
            upBtn.setOnClickListener{
                textViewName.clearFocus()
                upBtn.isEnabled = v < 9
                v++
                valueTextView.text = v.toString()
            }
            downBtn.setOnClickListener{
                textViewName.clearFocus()
                downBtn.isEnabled = v >= 1
                v--
                valueTextView.text = v.toString()
            }

            valueTextView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(text: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (text.isNullOrBlank()) {
                        valueTextView.text = "1"
                    } else {
                        downBtn.isEnabled = v > 1
                        upBtn.isEnabled = v < 9
                    }

                    toppingPrice = 0
                    toppings.forEach{ topping2 ->
                        val toppingName2 = Topping.values().single { tpp2 -> tpp2.toppingName == topping2 }
                        val viewId = resources.getIdentifier("checkBox${toppingName2.engName}", "id", packageName)
                        val checkbox = findViewById<CheckBox>(viewId)
                        val valueViewId2 = resources.getIdentifier("value${toppingName2.engName}", "id", packageName)
                        val valueTextView2 = findViewById<TextView>(valueViewId2)
                        if (checkbox.isChecked) toppingPrice += toppingName2.price * valueTextView2.text.toString().toInt()
                    }
                    var sum = sandPrice + toppingPrice
                    if(spinnerBread.selectedItem == Bread.NONE.breadName) sum += Bread.NONE.price
                    if(checkBoxFootLong.isChecked) sum += FootLong.FOOT_LONG.price
                    sumPrice.text = sum.toString()
                }
            })
        }
    }

    @SuppressLint("InflateParams")
    protected fun initAddDressingBtn(){
        addDressing.setOnClickListener{addBtn ->
            textViewName.clearFocus()
            addDressingCount++
            val selectDressingItemView = LayoutInflater.from(this).inflate(R.layout.select_dressing_item, null, false) as ViewGroup
            selectDressingItemView.id = selectDressingItemView.hashCode()
            select_dressing_container.addView(selectDressingItemView)

            val adapterDressing = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dressingsWoNothing)
            adapterDressing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDressing2.adapter = adapterDressing
            spinnerDressing2.setSelection(0)
            spinnerDressing2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            posi: Int, id: Long) {
                }

                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }

            val adapterDressingAmount = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, amountsDressing)
            adapterDressingAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDressingAmount2.adapter = adapterDressingAmount
            spinnerDressingAmount2.setSelection(1)
            spinnerDressingAmount2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View,
                                            posi: Int, id: Long) {
                }
                override fun onNothingSelected(arg0: AdapterView<*>) {}
            }
            addBtn.visibility = View.GONE
            addDressingText.visibility = View.GONE

            removeDressing.setOnClickListener{removeBtn ->
                textViewName.clearFocus()
                addDressing2.visibility = View.VISIBLE
                addDressingText2.visibility = View.VISIBLE
                textViewDressing2.visibility = View.GONE
                spinnerDressing2.visibility = View.GONE
                textViewDressingType2.visibility = View.GONE
                spinnerDressingAmount2.visibility = View.GONE
                textViewDressingAmount2.visibility = View.GONE
                howToDress.visibility = View.GONE
                removeBtn.visibility = View.GONE
                removeDressingText.visibility = View.GONE
            }
            
            addDressing2.setOnClickListener {addBtn2 ->
                textViewName.clearFocus()
                addDressing2Count++
                if (spinnerDressing.selectedItem != Dressing.NONE.dressingName){
                    addBtn2.visibility = View.GONE
                    addDressingText2.visibility = View.GONE
                    textViewDressing2.visibility = View.VISIBLE
                    spinnerDressing2.visibility = View.VISIBLE
                    textViewDressingType2.visibility = View.VISIBLE
                    spinnerDressingAmount2.visibility = View.VISIBLE
                    textViewDressingAmount2.visibility = View.VISIBLE
                    howToDress.visibility = View.VISIBLE
                    removeDressing.visibility = View.VISIBLE
                    removeDressingText.visibility = View.VISIBLE
                }
            }
            
        }
    }
    
    protected fun checkBoxFootLong(){
        val checkBoxFootLong = findViewById<CheckBox>(R.id.checkBoxFootLong)
        checkBoxFootLong.setOnClickListener{
            textViewName.clearFocus()
            if(checkBoxFootLong.isChecked){ 
                val sum = sandPrice + toppingPrice + FootLong.FOOT_LONG.price
                sumPrice.text = sum.toString()
            }else{ 
                val sum = sandPrice + toppingPrice
                sumPrice.text = sum.toString()
            }
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

