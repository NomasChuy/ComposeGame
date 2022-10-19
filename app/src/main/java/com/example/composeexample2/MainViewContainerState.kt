package com.example.composeexample2

sealed class MainViewContainerState {
    object ViewDice : MainViewContainerState()
    object ViewCoin : MainViewContainerState()
}