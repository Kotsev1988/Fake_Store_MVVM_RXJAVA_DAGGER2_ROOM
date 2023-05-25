package com.example.fakestore.data.room

import androidx.room.RoomDatabase

import com.example.fakestore.data.room.categories.dao.CategoriesDao
import com.example.fakestore.data.room.categories.entity.RoomCategories
import com.example.fakestore.data.room.favorites.dao.FavoritesDao
import com.example.fakestore.data.room.favorites.entity.RoomFavorites
import com.example.fakestore.data.room.myCart.dao.MyCardDao
import com.example.fakestore.data.room.myCart.entity.RoomMyCard
import com.example.fakestore.data.room.products.dao.ProductsDao
import com.example.fakestore.data.room.products.entity.RoomProducts

@androidx.room.Database(entities = [RoomCategories::class, RoomProducts::class, RoomMyCard::class, RoomFavorites::class], version = 4)
abstract class Database : RoomDatabase() {

    abstract val categoriesDao: CategoriesDao
    abstract val productsDao: ProductsDao
    abstract val myCardProducts: MyCardDao
    abstract val myFavorites: FavoritesDao

    companion object {
        const val DB_NAME = "database2.db"
    }
}