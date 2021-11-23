package com.hammer.app.subwaysimulator.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.localdata.Breads
import com.hammer.app.subwaysimulator.localdata.Dressings
import com.hammer.app.subwaysimulator.localdata.Toppings
import com.hammer.app.subwaysimulator.localdata.amounts
import com.hammer.app.subwaysimulator.localdata.breads
import com.hammer.app.subwaysimulator.localdata.dressings
import com.hammer.app.subwaysimulator.localdata.sandwiches
import com.hammer.app.subwaysimulator.model.Bread
import com.hammer.app.subwaysimulator.model.Recipe
import com.hammer.app.subwaysimulator.model.Sandwich
import com.hammer.app.subwaysimulator.model.Topping
import kotlinx.android.synthetic.main.create_recipe.checkBoxFootLong
import kotlinx.android.synthetic.main.create_recipe.checkBoxToast
import kotlinx.android.synthetic.main.create_recipe.checkBoxavocado
import kotlinx.android.synthetic.main.create_recipe.checkBoxbacon
import kotlinx.android.synthetic.main.create_recipe.checkBoxcheese
import kotlinx.android.synthetic.main.create_recipe.checkBoxcream
import kotlinx.android.synthetic.main.create_recipe.checkBoxegg
import kotlinx.android.synthetic.main.create_recipe.checkBoxmascar
import kotlinx.android.synthetic.main.create_recipe.checkBoxroastbeef
import kotlinx.android.synthetic.main.create_recipe.checkBoxshrimp
import kotlinx.android.synthetic.main.create_recipe.checkBoxtuna
import kotlinx.android.synthetic.main.create_recipe.completeButton
import kotlinx.android.synthetic.main.create_recipe.spinnerBread
import kotlinx.android.synthetic.main.create_recipe.spinnerCarrot
import kotlinx.android.synthetic.main.create_recipe.spinnerDressing
import kotlinx.android.synthetic.main.create_recipe.spinnerDressingAmount
import kotlinx.android.synthetic.main.create_recipe.spinnerGreenpepper
import kotlinx.android.synthetic.main.create_recipe.spinnerHotpepper
import kotlinx.android.synthetic.main.create_recipe.spinnerLettuce
import kotlinx.android.synthetic.main.create_recipe.spinnerOlive
import kotlinx.android.synthetic.main.create_recipe.spinnerPickles
import kotlinx.android.synthetic.main.create_recipe.spinnerRedonion
import kotlinx.android.synthetic.main.create_recipe.spinnerSand
import kotlinx.android.synthetic.main.create_recipe.spinnerTomato
import kotlinx.android.synthetic.main.create_recipe.sumPrice
import kotlinx.android.synthetic.main.create_recipe.textViewName
import kotlinx.android.synthetic.main.create_recipe.valueavocado
import kotlinx.android.synthetic.main.create_recipe.valuebacon
import kotlinx.android.synthetic.main.create_recipe.valuecheese
import kotlinx.android.synthetic.main.create_recipe.valuecream
import kotlinx.android.synthetic.main.create_recipe.valueegg
import kotlinx.android.synthetic.main.create_recipe.valuemascar
import kotlinx.android.synthetic.main.create_recipe.valueroastbeef
import kotlinx.android.synthetic.main.create_recipe.valueshrimp
import kotlinx.android.synthetic.main.create_recipe.valuetuna
import kotlinx.android.synthetic.main.select_dressing_item.removeDressing
import kotlinx.android.synthetic.main.select_dressing_item.spinnerDressing2
import kotlinx.android.synthetic.main.select_dressing_item.spinnerDressingAmount2
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateRecipeActivity : AbstractRecipeActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_recipe)

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
                    .setMessage("レシピを保存しますか？")
                    .setPositiveButton("はい") { _, _ ->
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
                        val recipe = Recipe(
                            sandwich = Sandwich.from(
                                spinnerSand.selectedItem as String,
                                if (spinnerBread.selectedItem.toString() == Breads.NONE.breadName) false else checkBoxFootLong.isChecked
                            ),
                            bread = Bread.from(spinnerBread.selectedItem as String, checkBoxToast.isChecked),
                            toppingList = toppingList
                        ).apply {
                            name = textViewName.text.toString()
                            price = sumPrice.text.toString().toInt()
                            createTime = sdf.format(c.time)
                            lettuce = spinnerLettuce.selectedItem as String
                            tomato = spinnerTomato.selectedItem as String
                            greenpepper = spinnerGreenpepper.selectedItem as String
                            redonion = spinnerRedonion.selectedItem as String
                            carrot = spinnerCarrot.selectedItem as String
                            pickles = spinnerPickles.selectedItem as String
                            olive = spinnerOlive.selectedItem as String
                            hotpepper = spinnerHotpepper.selectedItem as String
                            dressing.add(spinnerDressing.selectedItem as String)
                            if (dressing[0] == Dressings.NONE.dressingName) {
                                dressingAmount.add("-")
                            } else {
                                dressingAmount.add(spinnerDressingAmount.selectedItem as String)
                            }
                            if (addDressingCount == 1 && dressing[0] != Dressings.NONE.dressingName && removeDressing.visibility == View.VISIBLE) {
                                dressing.add(spinnerDressing2.selectedItem as String)
                                dressingAmount.add(spinnerDressingAmount2.selectedItem as String)
                                val howToDressRadioBtn = findViewById<RadioGroup>(R.id.howToDress)
                                val checkedRadioBtn = findViewById<RadioButton>(howToDressRadioBtn.checkedRadioButtonId)
                                howToDress = checkedRadioBtn.text.toString()
                            } else {
                                dressing.add("")
                                dressingAmount.add("")
                            }
                            e.putString(recipeId.id, gson.toJson(this))
                        }
                        e.apply()
                        Toast.makeText(this, "レシピ名: ${recipe.name} を作成しました", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .setNegativeButton("いいえ", null)
                    .show()
            }
        }
    }
}
