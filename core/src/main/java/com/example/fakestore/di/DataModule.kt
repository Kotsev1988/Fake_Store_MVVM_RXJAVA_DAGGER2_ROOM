package com.example.fakestore.di

import com.example.fakestore.data.GetAllProducts
import com.example.fakestore.data.GetCategoriesImpl
import com.example.fakestore.data.GetProductsImpl
import com.example.fakestore.data.MyCardProductsImpl
import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.ICategoriesCache
import com.example.fakestore.data.room.cache.IProductsCache
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.network.INetworkStates
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun categoriesData(
        api: IStoreAPI,
        networkStatus: INetworkStates,
        categoriesCache: ICategoriesCache,
    ): IGetCategories {
        return GetCategoriesImpl(api, networkStatus, categoriesCache)
    }


    @Singleton
    @Provides
    fun productsData(
        api: IStoreAPI,
        networkStatus: INetworkStates,
        productsCache: IProductsCache,
    ): IGetProducts {
        return GetProductsImpl(api, networkStatus, productsCache)
    }

    @Singleton
    @Provides
    fun productsAllData(
        api: IStoreAPI,
        networkStatus: INetworkStates,
    ): IGetAllProducts =
        GetAllProducts(api, networkStatus)

    @Singleton
    @Provides
    fun myCardProduct(
        db: Database
    ): IMyCardProducts = MyCardProductsImpl(db)


}