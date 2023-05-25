package com.example.product_detail_feature.presentation.viewModel.appState

import com.example.fakestore.productsEntity.ProductsItem

sealed class ProductAppState{
    data class OnSuccess(
        val bestSellers: ProductsItem
    ) : ProductAppState()

    data class IsContain(
        val isContain: Boolean
    ) : ProductAppState()

    data class AddToCard(
        val isAdded: Boolean
    ) : ProductAppState()
    data class Error(val error: String) : ProductAppState()
}
