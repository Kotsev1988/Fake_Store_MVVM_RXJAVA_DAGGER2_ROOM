package com.example.fakestore.data

import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.data.room.cache.ICategoriesCache
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.network.INetworkStates
import com.example.fakestore.productsEntity.Categories
import com.example.fakestore.productsEntity.Category
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetCategoriesImpl(private val api: IStoreAPI, private val networkStatus: INetworkStates, private val categoriesCache: ICategoriesCache):
    IGetCategories {

    override fun getCategories(): Single<Categories> = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline){
            api.getAllCategories().flatMap { categories ->
                val categoriesForDB = Categories()
                categories.map {
                    categoriesForDB.add(Category( it, false))
                }

                categoriesCache.insertCategoriesToDB(categoriesForDB).map {
                    it
                }
            }
        }else{
            categoriesCache.getCategoriesFromCache()
        }
    }.subscribeOn(Schedulers.io())
}