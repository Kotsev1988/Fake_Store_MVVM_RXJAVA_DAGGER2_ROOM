package com.example.fakestore.presentation.view.list

interface ProductItemView: IItemProductView {
    fun setTitle(title: String)
    fun setPrice(price: Int)
    fun setProductImage(url: String)

    fun clickLike()
    fun setDraw()
}