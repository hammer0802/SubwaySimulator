package com.hammer.app.subwaysimulator.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.hammer.app.subwaysimulator.BuildConfig
import com.hammer.app.subwaysimulator.R
import com.hammer.app.subwaysimulator.model.Recipe
import com.hammer.app.subwaysimulator.ui.top.TopScreen
import kotlinx.android.synthetic.main.activity_main.create
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.recycler_view
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeList = preference.all.values.filterIsInstance(String::class.java).map {value ->
            Json.decodeFromString(Recipe.serializer(), value)
        }.toList()
        setContent {
            TopScreen(recipeList.sortedBy { it.createTime })
        }
    }
}