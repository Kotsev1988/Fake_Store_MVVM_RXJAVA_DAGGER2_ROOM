package com.example.fakestore.presentation.view

interface IMyProductsView: IProductsView {
    fun setText(text: String)
    fun setPrice(price: String)
    fun loadAvatar(url: String)
    fun setCounter(counter: String)
}