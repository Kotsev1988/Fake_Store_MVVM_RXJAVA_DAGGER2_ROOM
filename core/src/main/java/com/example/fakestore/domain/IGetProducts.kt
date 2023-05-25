package com.example.fakestore.domain

import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single
import javax.inject.Singleton

@Singleton

interface IGetProducts {

    fun getProductByCategory(category: String): Single<List<ProductsItem>>


}