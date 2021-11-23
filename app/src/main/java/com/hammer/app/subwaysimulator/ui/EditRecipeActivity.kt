package com.hammer.app.subwaysimulator.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.localdata.AccentVegetables
import com.hammer.app.subwaysimulator.localdata.Amounts
import com.hammer.app.subwaysimulator.localdata.AmountsDressing
import com.hammer.app.subwaysimulator.localdata.Breads
import com.hammer.app.subwaysimulator.localdata.Dressings
import com.hammer.app.subwaysimulator.localdata.Sandwiches
import com.hammer.app.subwaysimulator.localdata.Toppings
import com.hammer.app.subwaysimulator.localdata.Vegetables
import com.hammer.app.subwaysimulator.localdata.amounts
import com.hammer.app.subwaysimulator.localdata.amountsDressing
import com.hammer.app.subwaysimulator.localdata.breads
import com.hammer.app.subwaysimulator.localdata.dressings
import com.hammer.app.subwaysimulator.localdata.dressingsWoNothing
import com.hammer.app.subwaysimulator.localdata.sandwiches
import com.hammer.app.subwaysimulator.model.Bread
import com.hammer.app.subwaysimulator.model.Dressing
import com.hammer.app.subwaysimulator.model.Recipe
import com.hammer.app.subwaysimulator.model.Sandwich
import com.hammer.app.subwaysimulator.model.Topping
import kotlinx.android.synthetic.main.create_recipe.*
import kotlinx.android.synthetic.main.select_dressing_item.*
import java.text.SimpleDateFormat
import java.util.*

class EditRecipeActivity : AbstractRecipeActivity() {
    private val intentFromResult: Intent by lazy { this.intent }
    val key: String? by lazy { intentFromResult.getStringExtra("key") }
    private val recipe: Recipe by lazy { gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java) }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_recipe)
        spinner("sandwich", sandwiches, "spinnerSand")
        spinner("bread", breads, "spinnerBread")
        spinnerDefaultVege(amounts, "spinnerLettuce")
        spinnerDefaultVege(amounts, "spinnerTomato")
        spinnerDefaultVege(amounts, "spinnerGreenpepper")
        spinnerDefaultVege(amounts, "spinnerRedonion")
        spinnerDefaultVege(amounts, "spinnerCarrot")
        spinner("pickles", amounts, "spinnerPickles")
        spinner("olive", amounts, "spinnerOlive")
        spinner("hotpepper", amounts, "spinnerHotpepper")
        spinner("dressing", dressings, "spinnerDressing")
        dressingAmount()
        checkBox()
        initAddDressingBtn()
        counterBtn()
        checkBoxFootLong()
        recipe.apply {
            textViewName.setText(name)
            val selectedSand = Sandwiches.values().single { sandwich.type.sandName == it.sandName }
            if (selectedSand.isEnabled) spinnerSand.setSelection(selectedSand.ordinal)
            else spinnerSand.setSelection(0)
            checkBoxFootLong.isChecked = sandwich.isFootLong
            val selectedBread = Breads.values().single { bread.type.breadName == it.breadName }
            if (selectedBread.isEnabled) spinnerBread.setSelection(selectedBread.ordinal)
            else spinnerSand.setSelection(0)
            checkBoxToast.isChecked = bread.isToasted
            toppingList.forEach {
                when(it.type) {
                    Toppings.NATURAL_CHEESE -> {
                        checkBoxcheese.isChecked = true
                        countercheese.visibility = View.VISIBLE
                        valuecheese.text = it.amount.toString()
                    }
                    Toppings.CREAM_CHEESE -> {
                        checkBoxcream.isChecked = true
                        countercream.visibility = View.VISIBLE
                        valuecream.text = it.amount.toString()
                    }
                    Toppings.MASCARPONE_CHEESE -> {
                        checkBoxmascar.isChecked = true
                        countermascar.visibility = View.VISIBLE
                        valuemascar.text = it.amount.toString()
                    }
                    Toppings.EGG -> {
                        checkBoxegg.isChecked = true
                        counteregg.visibility = View.VISIBLE
                        valueegg.text = it.amount.toString()

                    }
                    Toppings.BACON -> {
                        checkBoxbacon.isChecked = true
                        counterbacon.visibility = View.VISIBLE
                        valuebacon.text = it.amount.toString()
                    }
                    Toppings.TUNA -> {
                        checkBoxtuna.isChecked = true
                        countertuna.visibility = View.VISIBLE
                        valuetuna.text = it.amount.toString()
                    }
                    Toppings.SHRIMP -> {
                        checkBoxshrimp.isChecked = true
                        countershrimp.visibility = View.VISIBLE
                        valueshrimp.text = it.amount.toString()
                    }
                    Toppings.AVOCADO -> {
                        checkBoxavocado.isChecked = true
                        counteravocado.visibility = View.VISIBLE
                        valueavocado.text = it.amount.toString()
                    }
                    Toppings.ROAST_BEEF -> {
                        checkBoxroastbeef.isChecked = true
                        counterroastbeef.visibility = View.VISIBLE
                        valueroastbeef.text = it.amount.toString()
                    }
                    else -> Unit
                }
            }
            spinnerLettuce.setSelection(Amounts.values().single { vegetableMap[Vegetables.lettuce] == it }.ordinal)
            spinnerTomato.setSelection(Amounts.values().single { vegetableMap[Vegetables.tomato] == it }.ordinal)
            spinnerGreenpepper.setSelection(Amounts.values().single { vegetableMap[Vegetables.greenpepper] == it }.ordinal)
            spinnerRedonion.setSelection(Amounts.values().single { vegetableMap[Vegetables.redonion] == it }.ordinal)
            spinnerCarrot.setSelection(Amounts.values().single { vegetableMap[Vegetables.carrot] == it }.ordinal)
            spinnerPickles.setSelection(Amounts.values().single { accentVegetableMap[AccentVegetables.pickles] == it }.ordinal)
            spinnerOlive.setSelection(Amounts.values().single { accentVegetableMap[AccentVegetables.olive] == it }.ordinal)
            spinnerHotpepper.setSelection(Amounts.values().single { accentVegetableMap[AccentVegetables.hotpepper] == it }.ordinal)
            val selected0Dressing = Dressings.values().single { dressing[0].type == it }
            if (selected0Dressing.isEnabled) spinnerDressing.setSelection(selected0Dressing.ordinal)
            else spinnerDressing.setSelection(0)

            if (checkBoxRecommend.isChecked && spinnerDressing.selectedItem != selectedSand.recommendDressing.dressingName) {
                checkBoxRecommend.isChecked = false
            }
            if (dressing[0].type != Dressings.NONE) spinnerDressingAmount.setSelection(
                AmountsDressing.values().single { dressing[0].amounts == it }.ordinal
            )
            if (dressing[1].type != Dressings.NONE) {
                addDressingCount++
                val selectDressingItemView = LayoutInflater.from(this@EditRecipeActivity)
                    .inflate(R.layout.select_dressing_item, null, false) as ViewGroup
                selectDressingItemView.id = selectDressingItemView.hashCode()
                select_dressing_container.addView(selectDressingItemView)

                val adapterDressing =
                    ArrayAdapter<String>(this@EditRecipeActivity, android.R.layout.simple_spinner_item, dressingsWoNothing)
                adapterDressing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDressing2.adapter = adapterDressing
                spinnerDressing2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>, view: View,
                        posi: Int, id: Long
                    ) {
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>) {}
                }

                val adapterDressingAmount =
                    ArrayAdapter<String>(this@EditRecipeActivity, android.R.layout.simple_spinner_item, amountsDressing)
                adapterDressingAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDressingAmount2.adapter = adapterDressingAmount
                spinnerDressingAmount2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>, view: View,
                        posi: Int, id: Long
                    ) {
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>) {}
                }
                addDressing.visibility = View.GONE
                addDressingText.visibility = View.GONE
                val selected1Dressing = Dressings.values().single { dressing[1].type == it }
                if (selected1Dressing.isEnabled) spinnerDressing2.setSelection(selected1Dressing.ordinal)
                else spinnerDressing2.setSelection(0)
                spinnerDressingAmount2.setSelection(AmountsDressing.values().single { dressing[1].amounts == it }.ordinal)

                val howToDressRadioGroup = findViewById<RadioGroup>(R.id.howToDress)

                removeDressing.setOnClickListener { removeBtn ->
                    textViewName.clearFocus()
                    addDressing2.visibility = View.VISIBLE
                    addDressingText2.visibility = View.VISIBLE
                    textViewDressing2.visibility = View.GONE
                    spinnerDressing2.visibility = View.GONE
                    textViewDressingType2.visibility = View.GONE
                    spinnerDressingAmount2.visibility = View.GONE
                    textViewDressingAmount2.visibility = View.GONE
                    howToDressRadioGroup.visibility = View.GONE
                    removeBtn.visibility = View.GONE
                    removeDressingText.visibility = View.GONE
                }

                addDressing2.setOnClickListener { addBtn2 ->
                    textViewName.clearFocus()
                    addDressing2Count++
                    if (spinnerDressing.selectedItem != Dressings.NONE.dressingName) {
                        addBtn2.visibility = View.GONE
                        addDressingText2.visibility = View.GONE
                        textViewDressing2.visibility = View.VISIBLE
                        spinnerDressing2.visibility = View.VISIBLE
                        textViewDressingType2.visibility = View.VISIBLE
                        spinnerDressingAmount2.visibility = View.VISIBLE
                        textViewDressingAmount2.visibility = View.VISIBLE
                        howToDressRadioGroup.visibility = View.VISIBLE
                        removeDressing.visibility = View.VISIBLE
                        removeDressingText.visibility = View.VISIBLE
                    }
                }
            }
        }

        completeButton.setOnClickListener {
            if (textViewName.text.toString().isEmpty() || textViewName.text.toString().isBlank()) {
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                    .setTitle("レシピの名前を入力してください")
                    .setNegativeButton("はい", null)
                    .show()
            } else if (addDressingCount == 1 && spinnerDressing.selectedItem == spinnerDressing2.selectedItem) {
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                    .setTitle("追加ドレッシングは元のドレッシングと違うものにしてください")
                    .setNegativeButton("はい", null)
                    .show()
            } else {
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                    .setTitle("確認")
                    .setMessage("レシピ編集を完了しますか？")
                    .setPositiveButton("はい") { _, _ ->
                        val intentToResult = Intent()
                        val e = preference.edit()
                        val c = Calendar.getInstance()
                        val sdf = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.JAPAN)
                        val toppingList = mutableListOf<Topping>().apply {
                            if (checkBoxcheese.isChecked) add(
                                Topping.from(
                                    Toppings.NATURAL_CHEESE,
                                    valuecheese.text.toString().toInt()
                                )
                            )
                            if (checkBoxcream.isChecked) add(
                                Topping.from(
                                    Toppings.CREAM_CHEESE,
                                    valuecream.text.toString().toInt()
                                )
                            )
                            if (checkBoxmascar.isChecked) add(
                                Topping.from(
                                    Toppings.MASCARPONE_CHEESE,
                                    valuemascar.text.toString().toInt()
                                )
                            )
                            if (checkBoxegg.isChecked) add(
                                Topping.from(
                                    Toppings.EGG,
                                    valueegg.text.toString().toInt()
                                )
                            )
                            if (checkBoxbacon.isChecked) add(
                                Topping.from(
                                    Toppings.BACON,
                                    valuebacon.text.toString().toInt()
                                )
                            )
                            if (checkBoxtuna.isChecked) add(
                                Topping.from(
                                    Toppings.TUNA,
                                    valuetuna.text.toString().toInt()
                                )
                            )
                            if (checkBoxshrimp.isChecked) add(
                                Topping.from(
                                    Toppings.SHRIMP,
                                    valueshrimp.text.toString().toInt()
                                )
                            )
                            if (checkBoxavocado.isChecked) add(
                                Topping.from(
                                    Toppings.AVOCADO,
                                    valueavocado.text.toString().toInt()
                                )
                            )
                            if (checkBoxroastbeef.isChecked) add(
                                Topping.from(
                                    Toppings.ROAST_BEEF,
                                    valueroastbeef.text.toString().toInt()
                                )
                            )
                        }
                        val vegetableMap = mapOf(
                            Vegetables.lettuce to Amounts.from(spinnerLettuce.selectedItem as String),
                            Vegetables.lettuce to Amounts.from(spinnerLettuce.selectedItem as String),
                            Vegetables.tomato to Amounts.from(spinnerTomato.selectedItem as String),
                            Vegetables.greenpepper to Amounts.from(spinnerGreenpepper.selectedItem as String),
                            Vegetables.redonion to Amounts.from(spinnerRedonion.selectedItem as String),
                            Vegetables.carrot to Amounts.from(spinnerCarrot.selectedItem as String)
                        )
                        val accentVegetableMap = mapOf(
                            AccentVegetables.pickles to Amounts.from(spinnerPickles.selectedItem as String),
                            AccentVegetables.olive to Amounts.from(spinnerOlive.selectedItem as String),
                            AccentVegetables.hotpepper to Amounts.from(spinnerHotpepper.selectedItem as String),
                        )
                        val dressing = mutableListOf<Dressing>()
                        val firstAmountDressing =
                            if (spinnerDressing.selectedItem as String == Dressings.NONE.dressingName)
                                AmountsDressing.NONE
                            else
                                AmountsDressing.from(spinnerDressingAmount.selectedItem as String)
                        dressing.add(Dressing(Dressings.from(spinnerDressing.selectedItem as String), firstAmountDressing))
                        if (addDressingCount == 1 && dressing[0].type != Dressings.NONE && removeDressing.visibility == View.VISIBLE) {
                            dressing.add(
                                Dressing(
                                    Dressings.from(spinnerDressing2.selectedItem as String),
                                    AmountsDressing.from(spinnerDressingAmount2.selectedItem as String)
                                )
                            )
                        } else {
                            dressing.add(Dressing(Dressings.NONE, AmountsDressing.NONE))
                        }
                        Recipe(
                            sandwich = Sandwich.from(
                                spinnerSand.selectedItem as String,
                                if (spinnerBread.selectedItem.toString() == Breads.NONE.breadName) false else checkBoxFootLong.isChecked
                            ),
                            bread = Bread.from(spinnerBread.selectedItem as String, checkBoxToast.isChecked),
                            toppingList = toppingList,
                            vegetableMap = vegetableMap,
                            accentVegetableMap = accentVegetableMap,
                            dressing = dressing
                        ).apply {
                            name = textViewName.text.toString()
                            price = sumPrice.text.toString().toInt()
                            editTime = sdf.format(c.time)
                            val howToDressRadioBtn = findViewById<RadioGroup>(R.id.howToDress)
                            val checkedRadioBtn = findViewById<RadioButton>(howToDressRadioBtn.checkedRadioButtonId)
                            howToDress = checkedRadioBtn.text.toString()
                            e.putString(key, gson.toJson(this))
                        }
                        e.apply()
                        intentToResult.putExtra("key", key)
                        Toast.makeText(this, "レシピを編集しました", Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK, intentToResult)
                        finish()
                    }
                    .setNegativeButton("いいえ", null)
                    .show()
            }
        }
    }
}
