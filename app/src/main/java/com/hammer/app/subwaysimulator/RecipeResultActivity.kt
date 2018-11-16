package com.hammer.app.subwaysimulator

import android.app.AlertDialog
import android.content.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_recipe_result.*
import kotlinx.android.synthetic.main.content_recipe_result.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
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
const val RESULT_EDIT = 1000

class RecipeResultActivity : AppCompatActivity() {

    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    private val gson = Gson()
    private val intentFromList: Intent by lazy { this.intent }
    val key: String by lazy { intentFromList.getStringExtra("key")}
    private val recipe:Recipe by lazy { gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_recipe_result)
            setSupportActionBar(toolbar)

        setRecipeText(recipe)

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
                val intent= Intent(this, EditRecipeActivity::class.java)
                intent.putExtra("key", key)
                startActivityForResult(intent, RESULT_EDIT)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?){
        super.onActivityResult(requestCode, resultCode, intent)
        if(resultCode == RESULT_OK && requestCode == RESULT_EDIT && null != intent){
            clearRecipeText()
            val key = intent.getStringExtra("key")
            val recipe= gson.fromJson<Recipe>(preference.getString(key, ""), Recipe::class.java)
            setRecipeText(recipe)
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
        registerDatabase(Environment.getExternalStorageDirectory().path + "/SubwaySimulator/${recipe.createTime}.png")
        Toast.makeText(this, "レシピ画像を保存しました", Toast.LENGTH_SHORT).show()
    }


    private fun registerDatabase(file: String){
        val contentValues = ContentValues()
        val contentResolver = this.contentResolver
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        contentValues.put("_data", file)
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    private fun setRecipeText(recipe:Recipe){
        recipeName.append(":${recipe.name}")

        recipePrice.append(":${recipe.price}円")

        textViewSandType.text = recipe.sandwich
        if(recipe.footLong) textViewSandType.append("(フットロング)")

        val toast: String
        val breadText :String
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
        textViewBreadType.text = breadText

        if(recipe.cheese) textViewToppingSelect.append("ナチュラルスライスチーズ × ${recipe.cheeseAmount}\n")
        if(recipe.cream) textViewToppingSelect.append("クリームタイプチーズ × ${recipe.creamAmount}\n")
        if(recipe.mascar) textViewToppingSelect.append("マスカルポーネチーズ × ${recipe.mascarAmount}\n")
        if(recipe.egg) textViewToppingSelect.append("たまご × ${recipe.eggAmount}\n")
        if(recipe.bacon) textViewToppingSelect.append("ベーコン × ${recipe.baconAmount}\n")
        if(recipe.tuna)textViewToppingSelect.append("ツナ × ${recipe.tunaAmount}\n")
        if(recipe.shrimp)textViewToppingSelect.append("えび × ${recipe.shrimpAmount}\n")
        if(recipe.avocado)textViewToppingSelect.append("アボカド × ${recipe.avocadoAmount}\n")
        if(recipe.roastbeef)textViewToppingSelect.append("ローストビーフ × ${recipe.roastbeefAmount}")
        if(!recipe.cheese && !recipe.cream && !recipe.mascar && !recipe.egg && !recipe.bacon && !recipe.tuna && !recipe.shrimp && !recipe.avocado && !recipe.roastbeef) textViewToppingSelect.text = "無し"

        if(recipe.lettuce != "普通") textViewVegetableAmount.append("レタス：${recipe.lettuce} ")
        if(recipe.tomato != "普通") textViewVegetableAmount.append("トマト：${recipe.tomato} ")
        if(recipe.greenpepper != "普通") textViewVegetableAmount.append("ピーマン：${recipe.greenpepper} ")
        if(recipe.redonion != "普通") textViewVegetableAmount.append("レッドオニオン：${recipe.redonion} ")
        if(recipe.carrot != "普通") textViewVegetableAmount.append("ニンジン：${recipe.carrot}")
        if(recipe.lettuce == "普通" && recipe.carrot == "普通" && recipe.greenpepper == "普通" && recipe.redonion == "普通" && recipe.carrot == "普通") textViewVegetableAmount.text = "全ての量：普通"

        if(recipe.olive != "無し") textViewFreeToppingAmount.append("オリーブ：${recipe.olive} ")
        if(recipe.pickles != "無し") textViewFreeToppingAmount.append("ピクルス：${recipe.pickles} ")
        if(recipe.hotpepper != "無し") textViewFreeToppingAmount.append("ホットペッパー：${recipe.hotpepper}")
        if(recipe.olive == "無し" && recipe.pickles == "無し" && recipe.hotpepper == "無し") textViewFreeToppingAmount.text = "無し"

        val dressingText: String = when(recipe.dressing[0]){
            "無し" -> recipe.dressing[0]
            else -> "${recipe.dressing[0]}(量:${recipe.dressingAmount[0]})"
        }
        textViewDressingType.text = dressingText
        if (recipe.dressing[1] != "" && recipe.dressing[0] != "無し") {
            textViewDressingType.append("\n × ${recipe.dressing[1]}(量:${recipe.dressingAmount[1]})")
            textViewDressingType.append("\nかけ方：${recipe.howToDress}")
        }
    }

    private fun clearRecipeText(){
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
