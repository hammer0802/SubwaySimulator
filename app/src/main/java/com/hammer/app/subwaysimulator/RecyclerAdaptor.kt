package com.hammer.app.subwaysimulator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson

class MyRecyclerAdapter(val activity:MainActivity):RecyclerView.Adapter<MyRecyclerViewHolder>() {
    private val preference: SharedPreferences by lazy { activity.getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    val gson = Gson()
    var list: MutableList<Recipe> = mutableListOf()
    fun reload(){
        val allKeys = preference.all.keys
        for (key in allKeys){
            list.add(gson.fromJson<Recipe>(preference!!.getString(key, ""), Recipe::class.java))
        }
        list.sortBy{it.createTime}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.list_recipe, parent,false)
        return MyRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int) {
        holder!!.v.findViewById<TextView>(R.id.recipe_name).text = list[position].name
        holder!!.v.findViewById<TextView>(R.id.recipe_name).append("("+ list[position].sandwich + ")")

        holder.v.setOnClickListener{v ->
            val intent2= Intent(activity,RecipeResultActivity::class.java)
            val keys = preference.all.keys
            for (key in keys){
                if(list[position].name == gson.fromJson<Recipe>(preference!!.getString(key, ""), Recipe::class.java).name) {
                    intent2.putExtra("key", key)
                }
            }
            activity.startActivity(intent2)
        }
        holder.v.setOnLongClickListener{
            val alertDialog = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
                    .setTitle("確認")
                    .setMessage("1度削除したレシピは復元できません。"+ "\n" +"このレシピを削除しますか？")
                    .setPositiveButton("はい") { _, _ ->
                        val e = preference.edit()
                        val keys = preference.all.keys
                        //ToDo：position指定無しで処理できないか考える。
                        keys.forEach { key ->
                            val findRecipe = list.find { recipe -> recipe.name == gson.fromJson<Recipe>(preference!!.getString(key, ""), Recipe::class.java).name }
                            e.remove(findRecipe!!.uuid)
                        }
                        e.apply()
                        list.removeAt(position)
                        notifyItemRemoved(position)
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
            return@setOnLongClickListener true
        }
    }
}

class MyRecyclerViewHolder(val v: View): RecyclerView.ViewHolder(v)

