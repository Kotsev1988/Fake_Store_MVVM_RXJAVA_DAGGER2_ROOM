package com.example.fakestore.presentation.presenter.list

import com.example.fakestore.presentation.view.IMyProductsView

interface IListCardProducts<V: IMyProductsView> {

    var itemClickListenerIncrease: ((V) -> Unit)?
    var itemClickListenerDecrease: ((V) -> Unit)?
    var itemClickListenerDelete: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int


}