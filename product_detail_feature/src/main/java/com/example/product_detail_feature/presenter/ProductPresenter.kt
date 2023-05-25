package com.example.product_detail_feature.presenter

import android.annotation.SuppressLint
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.productsEntity.ProductsItem
import com.example.product_detail_feature.presentation.view.ProductView
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class ProductPresenter(
    private val id: String?,
    private val uiScheduler: Scheduler,
    private val product: IGetProductById,
    private val myCard: IMyCardProducts,
) : MvpPresenter<ProductView>() {

    lateinit var productsItem: ProductsItem


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
        isContain()
    }

    @SuppressLint("CheckResult")
    private fun isContain() {
        if (id != null) {
            myCard.isContain(id).observeOn(uiScheduler).subscribe({
                if (it) {
                    viewState.productInMyCard()
                } else {
                    viewState.turnOnAddButton()
                }

            },
                {
                    viewState.onError(it)
                })
        }
    }

    private fun loadData() {

        if (id != null) {
            product.getProductById(id).observeOn(uiScheduler)
                .subscribe({ product ->
                    productsItem = product
                    viewState.setTitle(product.title)
                    viewState.setDescription(product.description)
                    viewState.setPrice((product.price).toString())
                    viewState.setImage(product.image)
                },

                    {
                        viewState.onError(it)
                    })
        }
    }

    fun addToCard() {
        productsItem?.let {
            myCard.insertProductToMyCard(productsItem).observeOn(uiScheduler)
                .subscribe({

                    viewState.addedToMyCard()
                }, {
                    viewState.onError(Throwable(it.message))
                })
        }

    }

}