package com.example.product_detail_feature.presentation.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.productsEntity.ProductsItem
import com.example.product_detail_feature.presentation.viewModel.appState.ProductAppState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(

    private val product: IGetProductById,
    private val myCard: IMyCardProducts,
): ViewModel() {
    private lateinit var productsItem: ProductsItem

    private val _product: MutableLiveData<ProductAppState> = MutableLiveData()
    val productItem: LiveData<ProductAppState>
        get() {
            return _product
        }

    fun init(id: String?) {

        if (id != null) {
            loadData(id)
            isContain(id)
        }


    }

    @SuppressLint("CheckResult")
    private fun isContain(id: String) {
        myCard.isContain(id).observeOn(AndroidSchedulers.mainThread()).subscribe({
                _product.value = ProductAppState.IsContain(it)
        },
            {
                _product.value = it.message?.let { it1 -> ProductAppState.Error(it1) }
            })
    }

    @SuppressLint("CheckResult")
    private fun loadData(id: String) {

        product.getProductById(id).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ product ->
                productsItem = product
                _product.value = ProductAppState.OnSuccess(product)
            },

                {
                    _product.value = it.message?.let { it1 -> ProductAppState.Error(it1) }
                })
    }

    fun addToCard() {
        productsItem.let {
            myCard.insertProductToMyCard(productsItem).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _product.value = ProductAppState.AddToCard(true)
                }, {
                    _product.value = it.message?.let { it1 -> ProductAppState.Error(it1) }
                })
        }

    }

}