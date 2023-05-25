package com.example.fakestore.presentation.presenter.list

import com.example.fakestore.presentation.view.list.IItemView

interface IListCategory<V: IItemView> {

    var itemClickListener: ((V) -> Unit)?
    fun setData(categories: com.example.fakestore.productsEntity.Categories)
    fun bindView(view: V)
    fun getCount(): Int
    fun clear()

    fun changeColorItem(pos: Int)
}