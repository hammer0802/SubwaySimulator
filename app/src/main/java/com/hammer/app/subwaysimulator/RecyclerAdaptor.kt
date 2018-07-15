package com.hammer.app.subwaysimulator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecyclerAdapter(val activity:MainActivity):RecyclerView.Adapter<MyRecyclerViewHolder>() {
    private val preference: SharedPreferences by lazy { activity.getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    var list: MutableList<Recipe> = mutableListOf<Recipe>()  //Jsonの設定
    fun reload(){
        list = gson.fromJson<MutableList<Recipe>>(preference.getString("list", ""), object : TypeToken<MutableList<Recipe>>() {}.type)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyRecyclerViewHolder {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.list_recipe, parent,false)
        return MyRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder?, position: Int) {
        holder!!.v.findViewById<TextView>(R.id.recipe_name).text=list[position].name
        holder!!.v.findViewById<TextView>(R.id.recipe_price).text=list[position].price.toString()
        //sharedpriferenceで保存されているpositionごとのcounter数値を、リストの右端に表示させる
        holder.v.setOnClickListener{v ->
            val intent1= Intent(activity,MainActivity::class.java)
            intent1.putExtra("position",position)
            activity.startActivity(intent1)
        }
    }
}

class MyRecyclerViewHolder(val v: View): RecyclerView.ViewHolder(v)