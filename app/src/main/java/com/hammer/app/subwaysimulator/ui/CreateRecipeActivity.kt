package com.hammer.app.subwaysimulator.ui

import android.os.Bundle
import android.app.AlertDialog
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.hammer.app.subwaysimulator.localdata.Breads
import com.hammer.app.subwaysimulator.localdata.Dressings
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.model.Recipe
import com.hammer.app.subwaysimulator.localdata.amounts
import com.hammer.app.subwaysimulator.localdata.breads
import com.hammer.app.subwaysimulator.localdata.dressings
import com.hammer.app.subwaysimulator.localdata.sandwiches
import kotlinx.android.synthetic.main.create_recipe.*
import kotlinx.android.synthetic.main.select_dressing_item.*
import java.text.SimpleDateFormat
import java.util.*

class CreateRecipeActivity : AbstractRecipeActivity() {
    private val recipe = Recipe()
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
            }else if(addDressingCount == 1 && spinnerDressing.selectedItem == spinnerDressing2.selectedItem){
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setTitle("追加ドレッシングは元のドレッシングと違うものにしてください")
                        .setNegativeButton("はい", null)
                        .show()
            }
            else {
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setTitle("確認")
                        .setMessage("レシピを保存しますか？")
                        .setPositiveButton("はい") { _, _ ->
                            val e = preference.edit()
                            val c = Calendar.getInstance()
                            val sdf = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.JAPAN)
                            recipe.apply { 
                                name = textViewName.text.toString()
                                price = sumPrice.text.toString().toInt()
                                createTime = sdf.format(c.time)
                                sandwich = spinnerSand.selectedItem as String
                                footLong = if(spinnerBread.selectedItem.toString() == Breads.NONE.breadName) false else checkBoxFootLong.isChecked
                                bread = spinnerBread.selectedItem as String
                                toast = checkBoxToast.isChecked
                                cheese = checkBoxcheese.isChecked
                                cream = checkBoxcream.isChecked
                                mascar = checkBoxmascar.isChecked
                                egg = checkBoxegg.isChecked
                                bacon = checkBoxbacon.isChecked
                                tuna = checkBoxtuna.isChecked
                                shrimp = checkBoxshrimp.isChecked
                                avocado = checkBoxavocado.isChecked
                                roastbeef = checkBoxroastbeef.isChecked
                                cheeseAmount = valuecheese.text.toString().toInt()
                                creamAmount = valuecream.text.toString().toInt()
                                mascarAmount = valuemascar.text.toString().toInt()
                                eggAmount = valueegg.text.toString().toInt()
                                baconAmount = valuebacon.text.toString().toInt()
                                tunaAmount = valuetuna.text.toString().toInt()
                                shrimpAmount = valueshrimp.text.toString().toInt()
                                avocadoAmount = valueavocado.text.toString().toInt()
                                roastbeefAmount = valueroastbeef.text.toString().toInt()


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
                                if(addDressingCount == 1 && dressing[0] != Dressings.NONE.dressingName && removeDressing.visibility == View.VISIBLE){
                                    dressing.add(spinnerDressing2.selectedItem as String)
                                    dressingAmount.add(spinnerDressingAmount2.selectedItem as String)
                                    val howToDressRadioBtn = findViewById<RadioGroup>(R.id.howToDress)
                                    val checkedRadioBtn = findViewById<RadioButton>(howToDressRadioBtn.checkedRadioButtonId)
                                    howToDress = checkedRadioBtn.text.toString()
                                }else {
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
