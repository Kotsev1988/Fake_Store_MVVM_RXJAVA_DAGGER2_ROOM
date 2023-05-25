package com.example.fakestore.data.room.cache

import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IProductsCache {

    fun insertProductsToDB(category: String, products: List<ProductsItem>): Single<List<ProductsItem>>
    fun getProductsFromCache(category: String): Single<List<ProductsItem>>
     fun updateFavorite(productsItem: ProductsItem): Completable

}