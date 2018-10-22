package com.hammer.app.subwaysimulator

import android.os.Bundle
import android.app.AlertDialog
import android.view.View
import android.widget.RadioButton
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
            if (textViewName.text.toString() == "") {
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setTitle("Caution !")
                        .setMessage("レシピの名前を入力してください")
                        .setNegativeButton("はい", null)
                        .show()
            } else {
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setTitle("確認")
                        .setMessage("レシピを保存しますか？")
                        .setPositiveButton("はい") { _, _ ->
                            val uuid = UUID.randomUUID().toString()
                            val e = preference.edit()
                            recipe.name = textViewName.text.toString()
                            recipe.price = sumPrice.text.toString().toInt()
                            val c = Calendar.getInstance()
                            val sdf = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.JAPAN)
                            recipe.createTime = sdf.format(c.time)
                            recipe.sandwich = spinnerSand.selectedItem as String
                            recipe.footLong = checkBoxFootLong.isChecked
                            recipe.bread = spinnerBread.selectedItem as String
                            recipe.toast = checkBoxToast.isChecked
                            recipe.cheese = checkBoxcheese.isChecked
                            recipe.cream = checkBoxcream.isChecked
                            recipe.mascar = checkBoxmascar.isChecked
                            recipe.egg = checkBoxegg.isChecked
                            recipe.bacon = checkBoxbacon.isChecked
                            recipe.tuna = checkBoxtuna.isChecked
                            recipe.shrimp = checkBoxshrimp.isChecked
                            recipe.avocado = checkBoxavocado.isChecked
                            recipe.roastbeef = checkBoxroastbeef.isChecked
                            recipe.cheeseAmount = valuecheese.text.toString().toInt()
                            recipe.creamAmount = valuecream.text.toString().toInt()
                            recipe.mascarAmount = valuemascar.text.toString().toInt()
                            recipe.eggAmount = valueegg.text.toString().toInt()
                            recipe.baconAmount = valuebacon.text.toString().toInt()
                            recipe.tunaAmount = valuetuna.text.toString().toInt()
                            recipe.shrimpAmount = valueshrimp.text.toString().toInt()
                            recipe.avocadoAmount = valueavocado.text.toString().toInt()
                            recipe.roastbeefAmount = valueroastbeef.text.toString().toInt()
                            recipe.lettuce = spinnerLettuce.selectedItem as String
                            recipe.tomato = spinnerTomato.selectedItem as String
                            recipe.greenpepper = spinnerGreenpepper.selectedItem as String
                            recipe.redonion = spinnerRedonion.selectedItem as String
                            recipe.carrot = spinnerCarrot.selectedItem as String
                            recipe.pickles = spinnerPickles.selectedItem as String
                            recipe.olive = spinnerOlive.selectedItem as String
                            recipe.hotpepper = spinnerHotpepper.selectedItem as String
                            recipe.dressing.add(spinnerDressing.selectedItem as String)
                            if (recipe.dressing[0] == "無し") {
                                recipe.dressingAmount.add("-")
                            } else {
                                recipe.dressingAmount.add(spinnerDressingAmount.selectedItem as String)
                            }
                            if(addDressing.visibility == View.INVISIBLE){
                                recipe.dressing.add(spinnerDressing2.selectedItem as String)
                                recipe.dressingAmount.add(spinnerDressingAmount2.selectedItem as String)
                                val checkedRadioBtn = findViewById<RadioButton>(howToDress.checkedRadioButtonId)
                                recipe.howToDress = checkedRadioBtn.text.toString()
                            }
                            recipe.uuid = uuid
                            e.putString(uuid, gson.toJson(recipe))
                            e.apply()
                            finish()
                        }
                        .setNegativeButton("いいえ", null)
                        .show()
            }
        }
    }
}
