package com.example.fakestore.data.room.cache

import com.example.fakestore.productsEntity.ProductsLike
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IFavoritesCache {

    fun insertProductsToDB(id: Int, isFavorite: Boolean): Completable
    fun getProductsFromCache(): Single<ProductsLike>
}