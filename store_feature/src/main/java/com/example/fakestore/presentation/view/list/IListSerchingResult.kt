package com.example.fakestore.presentation.view.list

import androidx.appcompat.widget.SearchView
import com.example.fakestore.productsEntity.ProductsItem

interface IListSearchingResult<V: ISearchView> {
    var listener: SearchView.OnQueryTextListener ?
    var listenerClose: SearchView.OnCloseListener?



    var itemClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
    fun setData(list: ArrayList<ProductsItem>)
}