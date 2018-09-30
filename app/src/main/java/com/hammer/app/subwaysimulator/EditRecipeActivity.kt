package com.hammer.app.subwaysimulator

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.create_recipe.*
import java.text.SimpleDateFormat
import java.util.*

class EditRecipeActivity : AbstractRecipeActivity() {
    private val intent3: Intent by lazy { this.intent }
    val key: String by lazy { intent3.getStringExtra("key")}
    private val recipe:Recipe by lazy { gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java) }

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
                            val e = preference.edit()
                            recipe.name = textViewName.text.toString()
                            recipe.price = sumPrice.text.toString().toInt()
                            val c = Calendar.getInstance()
                            val sdf = SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.JAPAN)
                            recipe.editTime = sdf.format(c.time)
                            recipe.sandwich = spinnerSand.selectedItem as String
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
                            recipe.lettuce = spinnerLettuce.selectedItem as String
                            recipe.tomato = spinnerTomato.selectedItem as String
                            recipe.greenpepper = spinnerGreenpepper.selectedItem as String
                            recipe.redonion = spinnerRedonion.selectedItem as String
                            recipe.carrot = spinnerCarrot.selectedItem as String
                            recipe.pickles = spinnerPickles.selectedItem as String
                            recipe.olive = spinnerOlive.selectedItem as String
                            recipe.hotpepper = spinnerHotpepper.selectedItem as String
                            recipe.dressing = spinnerDressing.selectedItem as String
                            if (recipe.dressing == "無し") {
                                recipe.dressingAmount = "-"
                            } else {
                                recipe.dressingAmount = spinnerDressingAmount.selectedItem as String
                            }
                            e.putString(key, gson.toJson(recipe))
                            e.apply()
                            finish()
                        }
                        .setNegativeButton("いいえ", null)
                        .show()
            }
        }
    }
    override fun onResume() {
        super.onResume()

    }
}



