package com.example.fakestore.di

import android.content.Context
import androidx.room.Room
import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.ICategoriesCache
import com.example.fakestore.data.room.cache.IFavoritesCache
import com.example.fakestore.data.room.cache.IProductsCache
import com.example.fakestore.data.room.cache.room.CategoriesCache
import com.example.fakestore.data.room.cache.room.FavoriteCachesImpl
import com.example.fakestore.data.room.cache.room.ProductsCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(context: Context) = Room.databaseBuilder(context, Database::class.java, Database.DB_NAME).build()

    @Singleton
    @Provides
    fun categoryCache(database: Database): ICategoriesCache = CategoriesCache(database)

    @Singleton
    @Provides
    fun productsCache(database: Database): IProductsCache = ProductsCache(database)
    @Singleton
    @Provides
    fun favorites(database: Database): IFavoritesCache = FavoriteCachesImpl(database)

}