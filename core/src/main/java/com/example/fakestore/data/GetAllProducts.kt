package com.example.fakestore.data

import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.network.INetworkStates
import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GetAllProducts(private val api: IStoreAPI, private val networkStatus: INetworkStates):
    IGetAllProducts {
    override fun getAllProducts(category: String): Single<List<ProductsItem>> = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline){
            api.getAllProducts()
        }
        else{
           Single.create {
               it.onError(Throwable("No Internet Connection"))
           }
        }
    }.subscribeOn(Schedulers.io())
}