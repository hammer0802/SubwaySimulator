package com.hammer.app.subwaysimulator.main

import com.hammer.app.subwaysimulator.BasePresenter
import com.hammer.app.subwaysimulator.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {

        fun showRecipeList()

        fun showSortedList()

        fun showRecipeResult()

        fun showTutorialForciby()

        fun showPrivacyPolicy()

        fun showCreateRecipe()

    }

    interface Presenter : BasePresenter {
        fun removeListItem()
    }
}