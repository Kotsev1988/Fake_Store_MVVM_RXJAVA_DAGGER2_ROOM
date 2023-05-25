package com.example.fakestore.data

import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.data.room.cache.IProductCache
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.network.INetworkStates
import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetProduct(
    private val api: IStoreAPI,
    private val networkStatus: INetworkStates,
    private val productCache: IProductCache
): IGetProductById {

    override fun getProductById(id: String): Single<ProductsItem>  =

        networkStatus.isOnlineSingle().flatMap { isOnline ->

        if (isOnline){

            api.getProductById(id).flatMap {
                productCache.insertProductsToDB(it).map {
                    it
                }
            }?: Single.error<ProductsItem>(RuntimeException("Error "))
                .subscribeOn(Schedulers.io())

        }else{
           productCache.getProductsFromCache(id)
        }
    }.subscribeOn(Schedulers.io())

}