package com.example.fakestore.presentation.viewModel.appState

import com.example.fakestore.presentation.presenter.list.IListMyProducts
import com.example.fakestore.productsEntity.ProductsItem

sealed class MyCardAppState {
    data class OnSuccess(

        val myCardProducts: List<ProductsItem>,
        val productsListPresenter: IListMyProducts,

        ) : MyCardAppState()

    data class OnUpdate(

        val productsListPresenter: IListMyProducts,

        ) : MyCardAppState()

    data class TotalPrice(

        val totalPrice: String,

        ) : MyCardAppState()

    data class Error(val error: String) : MyCardAppState()
}