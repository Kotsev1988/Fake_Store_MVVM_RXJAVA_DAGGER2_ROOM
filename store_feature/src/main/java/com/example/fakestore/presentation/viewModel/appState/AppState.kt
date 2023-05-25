package com.example.fakestore.presentation.viewModel.appState

import com.example.fakestore.presentation.adapters.locationAndFilter.FilterClick
import com.example.fakestore.presentation.presenter.CategoryListPresenter
import com.example.fakestore.presentation.presenter.ProductsListPresenter
import com.example.fakestore.productsEntity.Categories
import com.example.fakestore.productsEntity.Category
import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsItem
import com.example.fakestore.productsEntity.ProductsLike

sealed class AppState {
    data class OnSuccess(
        val city: String,
        val category: Categories,
        val bestSellers: List<ProductsItem>,
        val categoryListPresenter: CategoryListPresenter,
        val productsListPresenter: ProductsListPresenter,
        val filterClick: FilterClick,
    ) : AppState()

    data class OnSearch(val category: Category) : AppState()
    data class Error(val error: String) : AppState()

    data class SetProducts(
        val city: String,
        val category: Categories,
        val bestSellers: List<ProductsItem>,
        val categoryListPresenter: CategoryListPresenter,
        val productsListPresenter: ProductsListPresenter,
        val filterClick: FilterClick,
    ) : AppState()

    data class updateLikes(
        val category: Categories,
        val bestSellers: List<ProductsItem>,
        val productsLike: ProductsLike,
        val categoryListPresenter: CategoryListPresenter,
        val productsListPresenter: ProductsListPresenter,
        val filterClick: FilterClick,
    ) : AppState()

    data class SetFilter(
        val category: Categories,
        val bestSellers: Products,
        val categoryListPresenter: CategoryListPresenter,
        val productsListPresenter: ProductsListPresenter,
        val filterClick: FilterClick,
    ) : AppState()

    data class SetLocation(
        val city: String,
        val category: Categories,
        val bestSellers: Products,
        val categoryListPresenter: CategoryListPresenter,
        val productsListPresenter: ProductsListPresenter,
        val filterClick: FilterClick,
    ) : AppState()

    object Loading : AppState()
    object ShowBottomDialog : AppState()
    object NavigateToSearch : AppState()


}
