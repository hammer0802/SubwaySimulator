package com.hammer.app.subwaysimulator

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.create_recipe.*
import kotlinx.android.synthetic.main.select_dressing_item.*
import java.text.SimpleDateFormat
import java.util.*

class EditRecipeActivity : AbstractRecipeActivity() {
    private val intentFromResult: Intent by lazy { this.intent }
    val key: String by lazy { intentFromResult.getStringExtra("key")}
    private val recipe:Recipe by lazy { gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java) }

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
            val selectedSand = Sandwiches.values().single { sandwich == it.sandName }
            if(selectedSand.isEnabled) spinnerSand.setSelection(selectedSand.number)
             else spinnerSand.setSelection(0)
            checkBoxFootLong.isChecked = footLong
            val selectedBread = Breads.values().single { bread == it.breadName }
            if(selectedBread.isEnabled) spinnerBread.setSelection(selectedBread.number)
            else spinnerSand.setSelection(0)
            checkBoxToast.isChecked = toast
            checkBoxcheese.isChecked = cheese
            checkBoxcream.isChecked = cream
            checkBoxmascar.isChecked = mascar
            checkBoxegg.isChecked = egg
            checkBoxbacon.isChecked = bacon
            checkBoxtuna.isChecked = tuna
            checkBoxshrimp.isChecked = shrimp
            checkBoxavocado.isChecked = avocado
            checkBoxroastbeef.isChecked = roastbeef
            if (cheese){
                countercheese.visibility = View.VISIBLE
                valuecheese.text = cheeseAmount.toString()
            }
            if (cream){
                countercream.visibility = View.VISIBLE
                valuecream.text = creamAmount.toString()
            }
            if (mascar){
                countermascar.visibility = View.VISIBLE
                valuemascar.text = mascarAmount.toString()
            }
            if (egg){
                counteregg.visibility = View.VISIBLE
                valueegg.text = eggAmount.toString()
            }
            if (bacon){
                counterbacon.visibility = View.VISIBLE
                valuebacon.text = baconAmount.toString()
            }
            if (tuna){
                countertuna.visibility = View.VISIBLE
                valuetuna.text = tunaAmount.toString()
            }
            if (shrimp){
                countershrimp.visibility = View.VISIBLE
                valueshrimp.text = shrimpAmount.toString()
            }
            if (avocado){
                counteravocado.visibility = View.VISIBLE
                valueavocado.text = avocadoAmount.toString()
            }
            if (roastbeef){
                counterroastbeef.visibility = View.VISIBLE
                valueroastbeef.text = roastbeefAmount.toString()
            }
            spinnerLettuce.setSelection(Amounts.values().single { lettuce == it.amount }.number)
            spinnerTomato.setSelection(Amounts.values().single { tomato == it.amount }.number)
            spinnerGreenpepper.setSelection(Amounts.values().single { greenpepper == it.amount }.number)
            spinnerRedonion.setSelection(Amounts.values().single { redonion == it.amount }.number)
            spinnerCarrot.setSelection(Amounts.values().single { carrot == it.amount }.number)
            spinnerPickles.setSelection(Amounts.values().single { pickles == it.amount }.number)
            spinnerOlive.setSelection(Amounts.values().single { olive == it.amount }.number)
            spinnerHotpepper.setSelection(Amounts.values().single { hotpepper == it.amount }.number)
            val selected0Dressing = Dressings.values().single { dressing[0] == it.dressingName }
            if (selected0Dressing.isEnabled) spinnerDressing.setSelection(selected0Dressing.number)
            else spinnerDressing.setSelection(0)

            val recommendDress = Dressings.values().single { selectedSand.recommendDressing == it.number }.dressingName
            if(checkBoxRecommend.isChecked && spinnerDressing.selectedItem != recommendDress){
                checkBoxRecommend.isChecked = false
            }
            if (dressing[0] != Dressings.NONE.dressingName) spinnerDressingAmount.setSelection(AmountsDressing.values().single { dressingAmount[0] == it.amount }.number)
            if (dressing[1] != ""){
                addDressingCount++
                val selectDressingItemView = LayoutInflater.from(this@EditRecipeActivity).inflate(R.layout.select_dressing_item, null, false) as ViewGroup
                selectDressingItemView.id = selectDressingItemView.hashCode()
                select_dressing_container.addView(selectDressingItemView)

                val adapterDressing = ArrayAdapter<String>(this@EditRecipeActivity, android.R.layout.simple_spinner_item, dressingsWoNothing)
                adapterDressing.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDressing2.adapter = adapterDressing
                spinnerDressing2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View,
                                                posi: Int, id: Long) {
                    }

                    override fun onNothingSelected(arg0: AdapterView<*>) {}
                }

                val adapterDressingAmount = ArrayAdapter<String>(this@EditRecipeActivity, android.R.layout.simple_spinner_item, amountsDressing)
                adapterDressingAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDressingAmount2.adapter = adapterDressingAmount
                spinnerDressingAmount2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View,
                                                posi: Int, id: Long) {
                    }
                    override fun onNothingSelected(arg0: AdapterView<*>) {}
                }
                addDressing.visibility = View.GONE
                addDressingText.visibility = View.GONE
                val selected1Dressing = Dressings.values().single { dressing[1] == it.dressingName }
                if(selected1Dressing.isEnabled) spinnerDressing2.setSelection(selected1Dressing.number)
                else spinnerDressing2.setSelection(0)
                spinnerDressingAmount2.setSelection(AmountsDressing.values().single { dressingAmount[1] == it.amount }.number)

                val howToDressRadioGroup = findViewById<RadioGroup>(R.id.howToDress)

                removeDressing.setOnClickListener{removeBtn ->
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

                addDressing2.setOnClickListener {addBtn2 ->
                    textViewName.clearFocus()
                    addDressing2Count++
                    if (spinnerDressing.selectedItem != Dressings.NONE.dressingName){
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
            } else if(addDressingCount == 1 && spinnerDressing.selectedItem == spinnerDressing2.selectedItem){
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
                            recipe.apply {
                                name = textViewName.text.toString()
                                price = sumPrice.text.toString().toInt()
                                editTime = sdf.format(c.time)
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
                                dressing[0] = spinnerDressing.selectedItem as String
                                if (dressing[0] == Dressings.NONE.dressingName) {
                                    dressingAmount[0] = "-"
                                } else {
                                    dressingAmount[0] = spinnerDressingAmount.selectedItem as String
                                }
                                if(addDressingCount == 1 && dressing[0] != Dressings.NONE.dressingName && removeDressing.visibility == View.VISIBLE) {
                                    dressing[1] = spinnerDressing2.selectedItem as String
                                    dressingAmount[1] = spinnerDressingAmount2.selectedItem as String
                                    val howToDressRadioBtn = findViewById<RadioGroup>(R.id.howToDress)
                                    val checkedRadioBtn = findViewById<RadioButton>(howToDressRadioBtn.checkedRadioButtonId)
                                    howToDress = checkedRadioBtn.text.toString()
                                }
                                if(addDressing2.visibility == View.VISIBLE){
                                    dressing[1] = ""
                                    dressingAmount[1] = ""
                                }
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
