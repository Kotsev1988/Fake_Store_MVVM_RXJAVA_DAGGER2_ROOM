package com.example.fakestore.presentation.presenter.list

import com.example.fakestore.presentation.view.list.IItemProductView

interface IListProduct<V: IItemProductView> {

    var onItemClickListener: ((V) -> Unit)?
    var onItemClickLikeListener: ((V) -> Unit)?

    fun bindView(view: V)
    fun getCount(): Int
}