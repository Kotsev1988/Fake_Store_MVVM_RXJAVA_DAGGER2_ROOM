package com.example.fakestore.presentation.viewModel

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false

    fun goToProductDetailifNotHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}