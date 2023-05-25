package com.example.fakestore.data.room.cache

import com.example.fakestore.productsEntity.Categories
import io.reactivex.rxjava3.core.Single

interface ICategoriesCache {

    fun insertCategoriesToDB(categories: Categories): Single<Categories>
    fun getCategoriesFromCache(): Single<Categories>
}