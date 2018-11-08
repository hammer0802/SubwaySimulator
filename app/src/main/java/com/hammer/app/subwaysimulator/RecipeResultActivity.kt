package com.hammer.app.subwaysimulator

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipe_result.*
import android.text.method.TextKeyListener.clear
import android.text.method.TextKeyListener.clear
import kotlinx.android.synthetic.main.content_recipe_result.*
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.support.v4.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import android.widget.Toast
import com.facebook.FacebookSdk



class RecipeResultActivity : AppCompatActivity() {

    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    private val intent2: Intent by lazy { this.intent }
    val key: String by lazy { intent2.getStringExtra("key")}
    val recipe:Recipe by lazy { gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_recipe_result)
            setSupportActionBar(toolbar)

        val recipeName = findViewById<TextView>(R.id.recipeName)
        recipeName.append(":${recipe.name}")

        val recipePrice = findViewById<TextView>(R.id.recipePrice)
        recipePrice.append(":${recipe.price}円")

        val sandType = findViewById<TextView>(R.id.textViewSandType)
        sandType.text = recipe.sandwich
        if(recipe.footLong) sandType.append("(フットロング)")

        val breadType = findViewById<TextView>(R.id.textViewBreadType)
        var toast = ""
        var breadText = ""
        when{
            recipe.bread == "無し(サラダ, + 300円)" -> breadText = "無し(サラダ)"
            recipe.toast -> {
                toast = "トースト有り"
                breadText = "${recipe.bread}($toast)"
            }
            else -> {
            toast = "トースト無し"
            breadText = "${recipe.bread}($toast)"
            }
        }
        breadType.text = breadText

        val toppingSelect = findViewById<TextView>(R.id.textViewToppingSelect)
        if(recipe.cheese) toppingSelect.append("ナチュラルスライスチーズ × ${recipe.cheeseAmount}\n")
        if(recipe.cream) toppingSelect.append("クリームタイプチーズ × ${recipe.creamAmount}\n")
        if(recipe.mascar) toppingSelect.append("マスカルポーネチーズ × ${recipe.mascarAmount}\n")
        if(recipe.egg) toppingSelect.append("たまご × ${recipe.eggAmount}\n")
        if(recipe.bacon) toppingSelect.append("ベーコン × ${recipe.baconAmount}\n")
        if(recipe.tuna)toppingSelect.append("ツナ × ${recipe.tunaAmount}\n")
        if(recipe.shrimp)toppingSelect.append("えび × ${recipe.shrimpAmount}\n")
        if(recipe.avocado)toppingSelect.append("アボカド × ${recipe.avocadoAmount}\n")
        if(recipe.roastbeef)toppingSelect.append("ローストビーフ × ${recipe.roastbeefAmount}")
        if(recipe.cheese == false && recipe.cream == false && recipe.mascar == false && recipe.egg == false && recipe.bacon == false
                && recipe.tuna == false && recipe.shrimp == false && recipe.avocado == false && recipe.roastbeef == false) toppingSelect.text = "無し"

        val vegetableAmount = findViewById<TextView>(R.id.textViewVegetableAmount)
        if(recipe.lettuce != "普通") vegetableAmount.append("レタス：${recipe.lettuce} ")
        if(recipe.tomato != "普通") vegetableAmount.append("トマト：${recipe.tomato} ")
        if(recipe.greenpepper != "普通") vegetableAmount.append("ピーマン：${recipe.greenpepper} ")
        if(recipe.redonion != "普通") vegetableAmount.append("レッドオニオン：${recipe.redonion} ")
        if(recipe.carrot != "普通") vegetableAmount.append("ニンジン：${recipe.carrot}")
        if(recipe.lettuce == "普通" && recipe.carrot == "普通" && recipe.greenpepper == "普通" && recipe.redonion == "普通" && recipe.carrot == "普通") vegetableAmount.text = "全ての量：普通"

        val freeToppingAmount = findViewById<TextView>(R.id.textViewFreeToppingAmount)
        if(recipe.olive != "無し") freeToppingAmount.append("オリーブ：${recipe.olive} ")
        if(recipe.pickles != "無し") freeToppingAmount.append("ピクルス：${recipe.pickles} ")
        if(recipe.hotpepper != "無し") freeToppingAmount.append("ホットペッパー：${recipe.hotpepper}")
        if(recipe.olive == "無し" && recipe.pickles == "無し" && recipe.hotpepper == "無し") freeToppingAmount.text = "無し"

        val dressingType = findViewById<TextView>(R.id.textViewDressingType)
        var dressingText = ""
        when(recipe.dressing[0]){
            "無し" -> dressingText = recipe.dressing[0]
            else -> dressingText = recipe.dressing[0] + "(量:" + recipe.dressingAmount[0] + ")"
        }
        dressingType.text = dressingText
        if (recipe.dressing.count() == 2 && recipe.dressing[0] != "無し") {
            dressingType.append("\n × ${recipe.dressing[1]}(量:${recipe.dressingAmount[1]})")
            dressingType.append("\nかけ方：${recipe.howToDress}")
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_recipe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val intent3= Intent(this, EditRecipeActivity::class.java)
                intent3.putExtra("key", key)
                startActivity(intent3)
                true
            }
            R.id.action_sns ->{
                val defaultItem = 0 // デフォルトでチェックされているアイテム
                val checkedItems = ArrayList<Int>()
                checkedItems.add(defaultItem)
                val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setTitle("共有するアプリを選択してください")
                        .setSingleChoiceItems(shareApp, defaultItem){ _, which ->
                            checkedItems.clear()
                            checkedItems.add(which)
                        }
                        .setPositiveButton("決定"){ _, _ ->
                            if (!checkedItems.isEmpty()) {
                                val bmp = Bitmap.createBitmap(recipeLayout.width, recipeLayout.height, Bitmap.Config.ARGB_8888)
                                val canvas = Canvas(bmp)
                                recipeLayout.draw(canvas)

                                val cachePath = File(this.cacheDir, "images")
                                cachePath.mkdirs()
                                val filePath = File(cachePath, "SubwayRecipe.png")
                                val fos = FileOutputStream(filePath.absolutePath)
                                bmp.compress(Bitmap.CompressFormat.PNG, 95, fos)
                                fos.close()
                                val contentUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", filePath)
                                val shareIntent = Intent()

                                when(shareApp[checkedItems[0]]) {
                                    "Twitter" -> {
                                        shareIntent.action = Intent.ACTION_SEND
                                        shareIntent.`package` = "com.twitter.android"
                                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
                                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "test #SubwaySimulator")
                                        try {
                                            startActivity(shareIntent)
                                        } catch (e: Exception){
                                            Toast.makeText(applicationContext, "Twitterアプリがインストールされていません", Toast.LENGTH_LONG)
                                                    .show()
                                        }
                                    }
                                    "Facebook" -> {
                                        shareIntent.action = Intent.ACTION_SEND
                                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
                                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "#SubwaySimulator")
                                        startActivity(Intent.createChooser(shareIntent, "アプリを選ぶ"))
                                    }
                                    "LINE" -> {
                                        shareIntent.action = Intent.ACTION_EDIT
                                        /* ↓文章を共有(いらないかも？なので一旦コメントアウト)
                                        val message = Uri.encode("テスト #SubwaySimulator")
                                        shareIntent.data= Uri.parse("line://msg/text/$message") */

                                        shareIntent.data= Uri.parse("line://msg/image/$filePath")
                                        try {
                                            startActivity(shareIntent)
                                        } catch (e: Exception) {
                                            Toast.makeText(applicationContext, "LINEアプリがインストールされていません", Toast.LENGTH_LONG)
                                                    .show()
                                        }
                                    }
                                    "その他" -> {
                                        shareIntent.action = Intent.ACTION_SEND
                                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
                                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                                        shareIntent.putExtra(Intent.EXTRA_TEXT, "#SubwaySimulator")
                                        startActivity(Intent.createChooser(shareIntent, "共有するアプリを選ぶ"))
                                    }
                                }
                            }
                        }
                        .setNegativeButton("キャンセル", null)
                        .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
