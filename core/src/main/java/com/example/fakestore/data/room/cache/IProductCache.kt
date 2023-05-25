package com.example.fakestore.data.room.cache

import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single

interface IProductCache {
    fun insertProductsToDB(products: ProductsItem): Single<ProductsItem>
    fun getProductsFromCache(id: String): Single<ProductsItem>

}