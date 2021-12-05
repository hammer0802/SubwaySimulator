package com.hammer.app.subwaysimulator.ui.top

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hammer.app.subwaysimulator.core.navigation.NavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor() : ViewModel() {
    private val _navigationEvent = Channel<NavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun openTutorial() {
        sendEvent(Nav.OpenTutorial)
    }

    fun openPrivacyPolicy() {
        sendEvent(Nav.OpenPrivacyPolicy)
    }

    fun openCreateRecipeScreen() {
        sendEvent(Nav.OpenCreateRecipeScreen)
    }

    fun openRecipeDetailScreen() {
        sendEvent(Nav.OpenRecipeDetailScreen)
    }

    private fun sendEvent(nav: Nav) {
        viewModelScope.launch {
            _navigationEvent.send(nav)
        }
    }

    sealed class Nav : NavigationEvent {
        object OpenTutorial : Nav()
        object OpenPrivacyPolicy : Nav()
        object OpenCreateRecipeScreen : Nav()
        object OpenRecipeDetailScreen : Nav()
    }
}
