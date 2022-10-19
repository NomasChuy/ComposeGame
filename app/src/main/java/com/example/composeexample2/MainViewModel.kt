package com.example.composeexample2

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _containerState = mutableStateOf<MainViewContainerState>(MainViewContainerState.ViewDice)
    val containerState : State<MainViewContainerState>
        get() = _containerState

    fun switchViews ( view : MainViewContainerState){
        _containerState.value = view
    }
}