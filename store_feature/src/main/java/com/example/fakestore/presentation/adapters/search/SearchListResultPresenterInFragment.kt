package com.example.fakestore.presentation.adapters.search

import androidx.appcompat.widget.SearchView
import com.example.fakestore.presentation.view.list.ILIstSearchingResultPresenter
import com.example.fakestore.presentation.view.list.ISearchListView
import com.example.fakestore.productsEntity.ProductsItem

class SearchListResultPresenterInFragment(
) : ILIstSearchingResultPresenter {
    var results = arrayListOf<ProductsItem>()

    override var itemClickListener: ((ISearchListView) -> Unit)? = null

    override var listener: SearchView.OnQueryTextListener? =null
    override var listenerClose: SearchView.OnCloseListener? = null


    override fun bindView(view: ISearchListView) {
        val result = results[view.pos]

        view.setText(result.title)
    }

    override fun getCount(): Int = results.size

    override fun setData(list: ArrayList<ProductsItem>) {
        results.clear()
        results.addAll(list)
    }
}