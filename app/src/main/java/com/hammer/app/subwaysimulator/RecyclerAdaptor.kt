package com.hammer.app.subwaysimulator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

class MyRecyclerAdapter(val activity:MainActivity):RecyclerView.Adapter<MyRecyclerViewHolder>() {
    private val preference: SharedPreferences by lazy { activity.getSharedPreferences("recipe", Context.MODE_PRIVATE) }
    private val gson = Gson()
    val list: MutableList<Recipe> = mutableListOf()
    fun reload(){
        list.clear()
        list.addAll(preference.all.values.filterIsInstance(String::class.java).map { value ->
           gson.fromJson<Recipe>(value, Recipe::class.java)
        }.toMutableList())
        list.sortBy{it.createTime}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_recipe, parent,false)
        return MyRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyRecyclerViewHolder, position: Int) {
        val listTitle = "${list[position].name}\n${list[position].sandwich}"
        holder.v.findViewById<TextView>(R.id.recipe_name).text = listTitle

        holder.v.setOnClickListener{
            holder.v.isEnabled = false
            val handler = Handler()
            val runnable = Runnable {
                holder.v.isEnabled = true
            }
            handler.postDelayed(runnable, 2000)
            val intentToResult= Intent(activity,RecipeResultActivity::class.java)
            intentToResult.putExtra("key", list[position].uuid)
            activity.startActivity(intentToResult)
        }
        holder.v.setOnLongClickListener{
            val alertDialog = AlertDialog.Builder(activity, R.style.MyAlertDialogStyle)
                    .setTitle("確認")
                    .setMessage("1度削除したレシピは復元できません。"+ "\n" +"レシピ名: ${list[position].name} を削除しますか？")
                    .setPositiveButton("はい") { _, _ ->
                        val deleteName = list[position].name
                        val e = preference.edit()
                        e.remove(list[position].uuid)
                        e.apply()
                        list.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, list.size)
                        Toast.makeText(activity, "レシピ名: $deleteName を削除しました", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
            return@setOnLongClickListener true
        }
    }
}

class MyRecyclerViewHolder(val v: View): RecyclerView.ViewHolder(v)

