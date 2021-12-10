package com.hammer.app.subwaysimulator.ui

enum class MyAppDestination {
    TOP,
    EDIT_RECIPE,
    RECIPE_DETAIL;

    companion object {
        fun fromRoute(route: String?): MyAppDestination {
            return when (route?.substringBefore("/")) {
                TOP.name -> TOP
                EDIT_RECIPE.name -> EDIT_RECIPE
                RECIPE_DETAIL.name -> RECIPE_DETAIL
                else -> TOP
            }
        }
    }
}
