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
import com.hammer.app.subwaysimulator.BuildConfig
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.localdata.AccentVegetables
import com.hammer.app.subwaysimulator.localdata.Amounts
import com.hammer.app.subwaysimulator.localdata.Breads
import com.hammer.app.subwaysimulator.localdata.Dressings
import com.hammer.app.subwaysimulator.localdata.Sandwiches
import com.hammer.app.subwaysimulator.localdata.Toppings
import com.hammer.app.subwaysimulator.localdata.Vegetables
import com.hammer.app.subwaysimulator.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_result.*
import kotlinx.android.synthetic.main.content_recipe_result.*
import kotlinx.serialization.json.Json
import net.taptappun.taku.kobayashi.runtimepermissionchecker.RuntimePermissionChecker
import java.io.File
import java.io.FileOutputStream

const val REQUEST_CODE = 1
const val RESULT_EDIT = 1000

class RecipeResultActivity : AppCompatActivity() {

    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    private val intentFromList: Intent by lazy { this.intent }
    val key: String? by lazy { intentFromList.getStringExtra("key") }
    private val recipe: Recipe by lazy { Json.decodeFromString(Recipe.serializer(), preference.getString(key, "") ?: "") }

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
                val isSandwichEnabled = Sandwiches.values().single { it.sandName == recipe.sandwich.type.sandName }.isEnabled
                val isBreadEnabled = Breads.values().single { it.breadName == recipe.bread.type.breadName }.isEnabled
                val isDressing0Enabled = Dressings.values().single { it == recipe.dressing[0].type }.isEnabled
                val isDressing1Enabled = recipe.dressing[1].type == Dressings.NONE || Dressings.values()
                    .single { it == recipe.dressing[1].type }.isEnabled

                if (!isSandwichEnabled || !isBreadEnabled || !isDressing0Enabled || !isDressing1Enabled) { //販売終了判定
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
            val recipe = Json.decodeFromString(Recipe.serializer(), preference.getString(key, "") ?: "")
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

        textViewSandType.text = recipe.sandwich.type.sandName
        if (recipe.sandwich.isFootLong) textViewSandType.append("(フットロング)")

        val toast: String
        val breadText: String
        when {
            recipe.bread.type == Breads.NONE -> breadText = "無し(サラダ)"
            recipe.bread.isToasted -> {
                toast = "トースト有り"
                breadText = "${recipe.bread.type.breadName}($toast)"
            }
            else -> {
                toast = "トースト無し"
                breadText = "${recipe.bread.type.breadName}($toast)"
            }
        }
        textViewBreadType.text = breadText

        textViewToppingSelect.apply {
            if (recipe.toppingList.isEmpty()) {
                text = Amounts.NONE.amount
                return@apply
            }
            recipe.toppingList.forEach {
                when (it.type) {
                    Toppings.NATURAL_CHEESE -> append("ナチュラルスライスチーズ × ${it.amount}\n")
                    Toppings.CREAM_CHEESE -> append("クリームタイプチーズ × ${it.amount}\n")
                    Toppings.MASCARPONE_CHEESE -> append("マスカルポーネチーズ × ${it.amount}\n")
                    Toppings.EGG -> append("たまご × ${it.amount}\n")
                    Toppings.BACON -> append("ベーコン × ${it.amount}\n")
                    Toppings.TUNA -> append("ツナ × ${it.amount}\n")
                    Toppings.SHRIMP -> append("えび × ${it.amount}\n")
                    Toppings.AVOCADO -> append("アボカド × ${it.amount}\n")
                    Toppings.ROAST_BEEF -> append("ローストビーフ × ${it.amount}\n")
                    else -> Unit
                }
            }
        }
        textViewVegetableAmount.apply {
            val vegetablesAmountNotNormal = recipe.vegetableMap.filterValues { it != Amounts.NORMAL }.keys
            if (vegetablesAmountNotNormal.isEmpty()) {
                text = "全ての量：普通"
                return@apply
            }
            vegetablesAmountNotNormal.forEach {
                val amount = recipe.vegetableMap[it]?.amount
                when (it) {
                    Vegetables.lettuce -> append("レタス：$amount ")
                    Vegetables.tomato -> append("トマト：$amount ")
                    Vegetables.greenpepper -> append("ピーマン：$amount ")
                    Vegetables.redonion -> append("レッドオニオン：$amount ")
                    Vegetables.carrot -> append("ニンジン：$amount")
                }
            }
        }
        textViewFreeToppingAmount.apply {
            val noneVegetables = recipe.accentVegetableMap.filterValues { it != Amounts.NONE }.keys
            if (noneVegetables.isEmpty()) {
                text = Amounts.NONE.amount
                return@apply
            }
            noneVegetables.forEach {
                val amount = recipe.accentVegetableMap[it]?.amount
                when (it) {
                    AccentVegetables.olive -> append("オリーブ：$amount ")
                    AccentVegetables.pickles -> append("ピクルス：$amount ")
                    AccentVegetables.hotpepper -> append("ホットペッパー：$amount")
                }
            }
        }

        val dressingText: String = when (recipe.dressing[0].type) {
            Dressings.NONE -> recipe.dressing[0].type.dressingName
            else -> "${recipe.dressing[0].type.dressingName}(量:${recipe.dressing[0].amounts.amount})"
        }
        textViewDressingType.text = dressingText
        if (recipe.dressing[1].type != Dressings.NONE && recipe.dressing[0].type != Dressings.NONE) {
            textViewDressingType.append("\n × ${recipe.dressing[1].type.dressingName}(量:${recipe.dressing[1].amounts.amount})")
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
