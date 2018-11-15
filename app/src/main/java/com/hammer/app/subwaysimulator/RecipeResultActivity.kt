package com.hammer.app.subwaysimulator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipe_result.*
import kotlinx.android.synthetic.main.content_recipe_result.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import android.support.v4.app.ShareCompat
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import net.taptappun.taku.kobayashi.runtimepermissionchecker.RuntimePermissionChecker

const val REQUEST_CODE = 1

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

        if(BuildConfig.DEBUG){
            //テスト用アプリID
            MobileAds.initialize(applicationContext, "ca-app-pub-3940256099942544~3347511713")
        }else {
            //本番アプリID
            MobileAds.initialize(applicationContext, "ca-app-pub-9742059950156424~8280793083")
        }

        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
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
                val builder = ShareCompat.IntentBuilder.from(this)
                builder.setChooserTitle("共有アプリを選択してください")
                builder.setType("image/png")
                builder.setStream(contentUri)
                builder.setText("#SubwaySimulator")
                builder.startChooser()
                true
            }
            R.id.action_save_image ->{
                try {
                    RuntimePermissionChecker.requestPermission(this, REQUEST_CODE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    if (RuntimePermissionChecker.hasSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) saveImage()
                }catch (e :Exception){
                    Toast.makeText(this, "画像を保存できませんでした", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveImage(){
        val bmp = Bitmap.createBitmap(recipeLayout.width, recipeLayout.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        recipeLayout.draw(canvas)
        val myDir = File(Environment.getExternalStorageDirectory().path, "/SubwaySimulator")
        if (!myDir.exists()) myDir.mkdirs()
        val filePath = File(myDir, "${recipe.createTime}.png")
        val fos = FileOutputStream(filePath.absolutePath)
        bmp.compress(Bitmap.CompressFormat.PNG, 95, fos)
        fos.close()
        Toast.makeText(this, "レシピ画像を保存しました", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (!RuntimePermissionChecker.hasSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) Toast.makeText(this, "ストレージ権限がありません", Toast.LENGTH_SHORT).show()
                else {
                    val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                            .setMessage("ストレージ権限がないため、アプリ情報から許可してください")
                            .setPositiveButton("アプリ情報"){_, _ ->
                                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            }
                            .setNegativeButton("キャンセル", null)
                            .show()
                }
            }
            if (RuntimePermissionChecker.hasSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) saveImage()
        }
    }

}
