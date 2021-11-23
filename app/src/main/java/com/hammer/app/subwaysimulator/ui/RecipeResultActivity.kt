package com.hammer.app.subwaysimulator.ui

import android.app.AlertDialog
import android.content.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.hammer.app.subwaysimulator.model.Amounts
import com.hammer.app.subwaysimulator.model.Breads
import com.hammer.app.subwaysimulator.BuildConfig
import com.hammer.app.subwaysimulator.model.Dressings
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.model.Sandwiches
import com.hammer.app.subwaysimulator.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_result.*
import kotlinx.android.synthetic.main.content_recipe_result.*
import net.taptappun.taku.kobayashi.runtimepermissionchecker.RuntimePermissionChecker
import java.io.File
import java.io.FileOutputStream

const val REQUEST_CODE = 1
const val RESULT_EDIT = 1000

class RecipeResultActivity : AppCompatActivity() {

    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    private val gson = Gson()
    private val intentFromList: Intent by lazy { this.intent }
    val key: String? by lazy { intentFromList.getStringExtra("key") }
    private val recipe: Recipe by lazy { gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_result)
        setSupportActionBar(toolbar)

        setRecipeText(recipe)

        if (BuildConfig.DEBUG) {
            //テスト用アプリID
            MobileAds.initialize(applicationContext, "ca-app-pub-3940256099942544~3347511713")
        } else {
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
                val intent = Intent(this, EditRecipeActivity::class.java)
                val isSandwichEnabled = Sandwiches.values().single { it.sandName == recipe.sandwich }.isEnabled
                val isBreadEnabled = Breads.values().single { it.breadName == recipe.bread }.isEnabled
                val isDressing0Enabled = Dressings.values().single { it.dressingName == recipe.dressing[0] }.isEnabled
                val isDressing1Enabled = recipe.dressing[1].isEmpty() || Dressings.values()
                    .single { it.dressingName == recipe.dressing[1] }.isEnabled

                if (!isSandwichEnabled || !isBreadEnabled || !isDressing0Enabled || !isDressing1Enabled || recipe.shredded) { //販売終了判定
                    val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setMessage("このレシピは販売終了メニューを含みます" + "\n" + "編集した場合、元のレシピに戻せません" + "\n" + "編集しますか？")
                        .setPositiveButton("はい") { _, _ ->
                            intent.putExtra("key", key)
                            startActivityForResult(intent, RESULT_EDIT)
                        }
                        .setNegativeButton("キャンセル", null)
                        .show()
                    true
                } else {
                    intent.putExtra("key", key)
                    startActivityForResult(intent, RESULT_EDIT)
                    true
                }
            }
            R.id.action_sns -> {
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
                builder.setText(" #SUBWAYシミュレーター https://subwaysimulator.page.link/kK5p")
                builder.startChooser()
                true
            }
            R.id.action_save_image -> {
                try {
                    RuntimePermissionChecker.requestPermission(
                        this,
                        REQUEST_CODE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    if (RuntimePermissionChecker.hasSelfPermission(
                            this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) saveImage()
                } catch (e: Exception) {
                    Toast.makeText(this, "画像を保存できませんでした", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!RuntimePermissionChecker.hasSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) Toast.makeText(
                    this,
                    "ストレージ権限がありません",
                    Toast.LENGTH_SHORT
                ).show()
                else {
                    val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                        .setMessage("ストレージ権限がないため、アプリ情報から許可してください")
                        .setPositiveButton("アプリ情報") { _, _ ->
                            val intent = Intent(
                                android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:$packageName")
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        .setNegativeButton("キャンセル", null)
                        .show()
                }
            }
            if (RuntimePermissionChecker.hasSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) saveImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK && requestCode == RESULT_EDIT && null != intent) {
            clearRecipeText()
            val key = intent.getStringExtra("key")
            val recipe = gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java)
            setRecipeText(recipe)
        }
    }

    private fun saveImage() {
        val bmp = Bitmap.createBitmap(recipeLayout.width, recipeLayout.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        recipeLayout.draw(canvas)
        val myDir = File(Environment.getExternalStorageDirectory().path, "/SubwaySimulator")
        if (!myDir.exists()) myDir.mkdirs()
        val filePath = File(myDir, "${recipe.createTime}.png")
        val fos = FileOutputStream(filePath.absolutePath)
        bmp.compress(Bitmap.CompressFormat.PNG, 95, fos)
        fos.close()
        registerDatabase(Environment.getExternalStorageDirectory().path + "/SubwaySimulator/${recipe.createTime}.png")
        Toast.makeText(this, "レシピ画像を保存しました", Toast.LENGTH_SHORT).show()
    }

    private fun registerDatabase(file: String) {
        val contentValues = ContentValues()
        val contentResolver = this.contentResolver
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        contentValues.put("_data", file)
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    private fun setRecipeText(recipe: Recipe) {
        recipeName.append(":${recipe.name}")

        recipePrice.append(":${recipe.price}円")

        textViewSandType.text = recipe.sandwich
        if (recipe.footLong) textViewSandType.append("(フットロング)")

        val toast: String
        val breadText: String
        when {
            recipe.bread == Breads.NONE.breadName -> breadText = "無し(サラダ)"
            recipe.toast -> {
                toast = "トースト有り"
                breadText = "${recipe.bread}($toast)"
            }
            else -> {
                toast = "トースト無し"
                breadText = "${recipe.bread}($toast)"
            }
        }
        textViewBreadType.text = breadText

        if (recipe.cheese) textViewToppingSelect.append("ナチュラルスライスチーズ × ${recipe.cheeseAmount}\n")
        if (recipe.cream) textViewToppingSelect.append("クリームタイプチーズ × ${recipe.creamAmount}\n")
        if (recipe.mascar) textViewToppingSelect.append("マスカルポーネチーズ × ${recipe.mascarAmount}\n")
        if (recipe.egg) textViewToppingSelect.append("たまご × ${recipe.eggAmount}\n")
        if (recipe.bacon) textViewToppingSelect.append("ベーコン × ${recipe.baconAmount}\n")
        if (recipe.tuna) textViewToppingSelect.append("ツナ × ${recipe.tunaAmount}\n")
        if (recipe.shrimp) textViewToppingSelect.append("えび × ${recipe.shrimpAmount}\n")
        if (recipe.avocado) textViewToppingSelect.append("アボカド × ${recipe.avocadoAmount}\n")
        if (recipe.roastbeef) textViewToppingSelect.append("ローストビーフ × ${recipe.roastbeefAmount}\n")

        //期間限定トッピング
        if (recipe.shredded) textViewToppingSelect.append("シュレッドチーズ × ${recipe.shreddedAmount}")

        val none = Amounts.NONE.amount
        if (!recipe.cheese && !recipe.cream && !recipe.mascar && !recipe.egg && !recipe.bacon && !recipe.tuna && !recipe.shrimp && !recipe.avocado && !recipe.roastbeef && !recipe.shredded) textViewToppingSelect.text =
            none

        val normal = Amounts.NORMAL.amount
        if (recipe.lettuce != normal) textViewVegetableAmount.append("レタス：${recipe.lettuce} ")
        if (recipe.tomato != normal) textViewVegetableAmount.append("トマト：${recipe.tomato} ")
        if (recipe.greenpepper != normal) textViewVegetableAmount.append("ピーマン：${recipe.greenpepper} ")
        if (recipe.redonion != normal) textViewVegetableAmount.append("レッドオニオン：${recipe.redonion} ")
        if (recipe.carrot != normal) textViewVegetableAmount.append("ニンジン：${recipe.carrot}")
        if (recipe.lettuce == normal && recipe.carrot == normal && recipe.greenpepper == normal && recipe.redonion == normal && recipe.carrot == normal) textViewVegetableAmount.text =
            "全ての量：普通"

        if (recipe.olive != none) textViewFreeToppingAmount.append("オリーブ：${recipe.olive} ")
        if (recipe.pickles != none) textViewFreeToppingAmount.append("ピクルス：${recipe.pickles} ")
        if (recipe.hotpepper != none) textViewFreeToppingAmount.append("ホットペッパー：${recipe.hotpepper}")
        if (recipe.olive == none && recipe.pickles == none && recipe.hotpepper == none) textViewFreeToppingAmount.text = none

        val dressingText: String = when (recipe.dressing[0]) {
            none -> recipe.dressing[0]
            else -> "${recipe.dressing[0]}(量:${recipe.dressingAmount[0]})"
        }
        textViewDressingType.text = dressingText
        if (!recipe.dressing[1].isEmpty() && recipe.dressing[0] != none) {
            textViewDressingType.append("\n × ${recipe.dressing[1]}(量:${recipe.dressingAmount[1]})")
            textViewDressingType.append("\nかけ方：${recipe.howToDress}")
        }
    }

    private fun clearRecipeText() {
        recipeName.text = "レシピ名"
        recipePrice.text = "金額"
        textViewSandType.text = ""
        textViewBreadType.text = ""
        textViewToppingSelect.text = ""
        textViewVegetableAmount.text = ""
        textViewFreeToppingAmount.text = ""
        textViewDressingType.text = ""
    }
}
