package com.example.fakestore.di

import android.app.Application
import android.widget.ImageView
import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.data.room.cache.IFavoritesCache
import com.example.fakestore.data.room.cache.IProductCache
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProductById
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.image.ILoadImage
import com.google.gson.Gson
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(

    modules = [
        AppModule::class,
        ApiModule::class,
        DatabaseModule::class,
        DataModule::class,
        LoadImageModule::class,
        ProductModule::class

    ])

interface BaseComponent {

    fun inject(app: Application)
    fun api(): IStoreAPI
    fun gson(): Gson
    fun productsData(): IGetProducts
    fun categoriesData(): IGetCategories
    fun productsAllData(): IGetAllProducts
    fun loadImage(): ILoadImage<ImageView>
    fun myCardProduct(
    ): IMyCardProducts

    fun productCache(): IProductCache

    fun productData(
    ): IGetProductById

    fun favorites(): IFavoritesCache





}