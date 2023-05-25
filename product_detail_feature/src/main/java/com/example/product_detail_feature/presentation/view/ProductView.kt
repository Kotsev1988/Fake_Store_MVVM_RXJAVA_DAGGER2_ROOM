package com.example.product_detail_feature.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ProductView: MvpView {

    fun setTitle(text: String)
    fun setImage(url: String)
    fun setDescription(description: String)
    fun setPrice(price: String)
    fun onError(e: Throwable)
    fun remove()

    fun addedToMyCard()
    fun productInMyCard()
    fun turnOnAddButton()

}