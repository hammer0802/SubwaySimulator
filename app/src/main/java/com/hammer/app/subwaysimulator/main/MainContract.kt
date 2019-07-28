package com.hammer.app.subwaysimulator.main

import com.hammer.app.subwaysimulator.BasePresenter
import com.hammer.app.subwaysimulator.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
    }

    interface Presenter : BasePresenter {

    }
}