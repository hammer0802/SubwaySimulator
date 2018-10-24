package com.hammer.app.subwaysimulator

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
import java.security.InvalidKeyException


abstract class AbstractRecipeActivity: AppCompatActivity(){
    val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    var sandPrice = 0
    var toppingPrice = 0

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
                    sandPrice = sandPrices[item].toString().toInt()
                    var sum = sandPrice + toppingPrice
                    if(checkBoxFootLong.isChecked) sum += 300
                    if(spinnerBread.selectedItem == "無し(サラダ, + 300円)") sum += 300
                    sumPrice.text = sum.toString()
                    if (checkboxRecommend.isChecked){
                        val spinnerDressing = findViewById<Spinner>(R.id.spinnerDressing)
                        spinnerDressing.setSelection(recommendDressing[item].toString().toInt())
                    }
                }
                if(itemName == "bread"){
                    val checkBoxToast = findViewById<CheckBox>(R.id.checkBoxToast)
                    if (spinner.selectedItem == "無し(サラダ, + 300円)") {
                        checkBoxToast.visibility = View.INVISIBLE
                        checkBoxFootLong.visibility = View.INVISIBLE
                        val sum = sandPrice + toppingPrice + 300
                        sumPrice.text = sum.toString()
                    } else {
                        checkBoxToast.visibility = View.VISIBLE
                        checkBoxFootLong.visibility = View.VISIBLE
                        var sum = sandPrice + toppingPrice
                        if (checkBoxFootLong.isChecked) sum += 300
                        sumPrice.text = sum.toString()
                    }
                }
                if (itemName == "dressing") {
                    val spinnerDressingAmount = findViewById<Spinner>(R.id.spinnerDressingAmount)
                    val textViewDressingAmount = findViewById<TextView>(R.id.textViewDressingAmount)
                    if (spinner.selectedItem == "無し") {
                        spinnerDressingAmount.visibility = View.INVISIBLE
                        textViewDressingAmount.visibility = View.INVISIBLE
                        if(addDressing.visibility == View.INVISIBLE){
                            spinnerDressing2.visibility = View.INVISIBLE
                            textViewDressingType2.visibility = View.INVISIBLE
                            spinnerDressingAmount2.visibility = View.INVISIBLE
                            textViewDressingAmount2.visibility = View.INVISIBLE
                            howToDress.visibility = View.INVISIBLE
                        }
                    } else {
                        spinnerDressingAmount.visibility = View.VISIBLE
                        textViewDressingAmount.visibility = View.VISIBLE
                        if(addDressing.visibility == View.INVISIBLE){
                            spinnerDressing2.visibility = View.VISIBLE
                            textViewDressingType2.visibility = View.VISIBLE
                            spinnerDressingAmount2.visibility = View.VISIBLE
                            textViewDressingAmount2.visibility = View.VISIBLE
                            howToDress.visibility = View.VISIBLE
                        }
                    }
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
        spinnerDressingAmount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        posi: Int, id: Long) {
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    protected fun checkBox() {
        toppings.forEach {topping ->
            val viewId = resources.getIdentifier("checkBox${toppingMap[topping]}", "id", packageName)
            val checkbox = findViewById<CheckBox>(viewId)
            val counterViewId = resources.getIdentifier("counter${toppingMap[topping]}", "id", packageName)
            val counter = findViewById<LinearLayout>(counterViewId)
            val checkBoxFootLong = findViewById<CheckBox>(R.id.checkBoxFootLong)
            checkbox.isChecked = false
            counter.visibility = View.INVISIBLE
            checkbox.text = topping
            checkbox.setOnClickListener {
                toppingPrice = 0
                toppings.forEach {topping2 ->
                    val viewId2 = resources.getIdentifier("checkBox${toppingMap[topping2]}", "id", packageName)
                    val checkbox2 = findViewById<CheckBox>(viewId2)
                    val counterViewId2 = resources.getIdentifier("counter${toppingMap[topping2]}", "id", packageName)
                    val counter2 = findViewById<LinearLayout>(counterViewId2)
                    val valueViewId = resources.getIdentifier("value${toppingMap[topping2]}", "id", packageName)
                    val valueEditText = findViewById<EditText>(valueViewId)
                    if (checkbox2.isChecked) {
                        counter2.visibility = View.VISIBLE
                        toppingPrice += toppingPrices[topping2].toString().toInt() * valueEditText.text.toString().toInt()
                    }else{
                        counter2.visibility = View.INVISIBLE
                    }
                }
                var sum = sandPrice + toppingPrice
                if(spinnerBread.selectedItem == "無し(サラダ, + 300円)") sum += 300
                if(checkBoxFootLong.isChecked) sum += 300
                sumPrice.text = sum.toString()
            }
        }
    }

    protected fun counterBtn(){
        toppings.forEach{topping ->
            val upBtnViewId = resources.getIdentifier("up${toppingMap[topping]}", "id", packageName)
            val upBtn = findViewById<ImageButton>(upBtnViewId)
            val valueViewId = resources.getIdentifier("value${toppingMap[topping]}", "id", packageName)
            val valueEditText = findViewById<EditText>(valueViewId)
            val downBtnViewId = resources.getIdentifier("down${toppingMap[topping]}", "id", packageName)
            val downBtn = findViewById<ImageButton>(downBtnViewId)
            val checkBoxFootLong = findViewById<CheckBox>(R.id.checkBoxFootLong)
            upBtn.setOnClickListener{
                var v = valueEditText.text.toString().toInt()
                upBtn.isEnabled = v < 9
                when {
                    v in 1..8 -> v++
                    v > 9 -> throw InvalidKeyException()
                }
                valueEditText.setText(v.toString())
            }
            downBtn.setOnClickListener{
                var v = valueEditText.text.toString().toInt()
                downBtn.isEnabled = v >= 1
                if (v <= 0) throw InvalidKeyException() else v--
                valueEditText.setText(v.toString())
            }

            valueEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(text: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (text.isNullOrBlank()) {
                        valueEditText.setText("1")
                    } else {
                        val v = valueEditText.text.toString().toInt()
                        downBtn.isEnabled = v > 1
                        upBtn.isEnabled = v < 9
                        when{
                            v < 1 -> valueEditText.setText("1")
                            v > 9 -> valueEditText.setText("9")
                            text.toString() != v.toString() -> valueEditText.setText(v.toString())
                        }
                    }
                    valueEditText.setSelection(valueEditText.length())

                    toppingPrice = 0
                    toppings.forEach{topping2 ->
                        val viewId = resources.getIdentifier("checkBox${toppingMap[topping2]}", "id", packageName)
                        val checkbox = findViewById<CheckBox>(viewId)
                        val valueViewId2 = resources.getIdentifier("value${toppingMap[topping2]}", "id", packageName)
                        val valueEditText2 = findViewById<EditText>(valueViewId2)
                        if (checkbox.isChecked) toppingPrice += toppingPrices[topping2].toString().toInt() * valueEditText2.text.toString().toInt()
                    }
                    var sum = sandPrice + toppingPrice
                    if(spinnerBread.selectedItem == "無し(サラダ, + 300円)") sum += 300
                    if(checkBoxFootLong.isChecked) sum += 300
                    sumPrice.text = sum.toString()
                }
            })
        }
    }

    protected fun initAddDressingBtn(){
        addDressing.setOnClickListener{
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
            addDressing.visibility = View.INVISIBLE
            addDressingText.visibility = View.INVISIBLE
            if(spinnerDressing.selectedItem == "無し"){
                spinnerDressing2.visibility = View.INVISIBLE
                textViewDressingType2.visibility = View.INVISIBLE
                spinnerDressingAmount2.visibility = View.INVISIBLE
                textViewDressingAmount2.visibility = View.INVISIBLE
                howToDress.visibility = View.INVISIBLE
            }
        }
    }
    
    protected fun checkBoxFootLong(){
        val checkBoxFootLong = findViewById<CheckBox>(R.id.checkBoxFootLong)
        checkBoxFootLong.setOnClickListener{
            if(checkBoxFootLong.isChecked){ 
                val sum = sandPrice + toppingPrice + 300
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

