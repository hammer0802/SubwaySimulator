package com.hammer.app.subwaysimulator.model

import com.hammer.app.subwaysimulator.localdata.AccentVegetables
import com.hammer.app.subwaysimulator.localdata.Amounts
import com.hammer.app.subwaysimulator.localdata.Vegetables
import java.util.UUID

data class Recipe(
    val recipeId: RecipeId = RecipeId.createRecipeId(),
    val name: String,
    val price: Int,
    val sandwich: Sandwich,
    val bread: Bread,
    val toppingList: List<Topping>,
    val vegetableMap: Map<Vegetables, Amounts>,
    val accentVegetableMap: Map<AccentVegetables, Amounts>,
    val dressing: List<Dressing>,
    val howToDress: String,
    val createTime: String,
    val editTime: String,
) {
    companion object {
        fun from(
            name: String,
            price: Int,
            sandwich: Sandwich,
            bread: Bread,
            toppingList: List<Topping>,
            vegetableMap: Map<Vegetables, Amounts>,
            accentVegetableMap: Map<AccentVegetables, Amounts>,
            dressing: List<Dressing>,
            howToDress: String,
            time: String,
        ): Recipe {
            return Recipe(
                name = name,
                price = price,
                sandwich = sandwich,
                bread = bread,
                toppingList = toppingList,
                vegetableMap = vegetableMap,
                accentVegetableMap = accentVegetableMap,
                dressing = dressing,
                howToDress = howToDress,
                createTime = time,
                editTime = time
            )
        }
    }
}

@JvmInline
value class RecipeId(val id: String) {
    companion object {
        fun createRecipeId(): RecipeId {
            return RecipeId(UUID.randomUUID().toString())
        }
    }
}