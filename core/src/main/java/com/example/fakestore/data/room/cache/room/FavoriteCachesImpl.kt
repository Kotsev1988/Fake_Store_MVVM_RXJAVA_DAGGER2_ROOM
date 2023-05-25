package com.example.fakestore.data.room.cache.room

import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.IFavoritesCache
import com.example.fakestore.data.room.favorites.entity.RoomFavorites
import com.example.fakestore.productsEntity.ProductsItemLikes
import com.example.fakestore.productsEntity.ProductsLike
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoriteCachesImpl(private val db: Database): IFavoritesCache {

    override fun insertProductsToDB(id: Int, isFavorite: Boolean): Completable = Completable.fromAction {
        val roomFavorites = RoomFavorites(
            id,
            isFavorite
        )
        db.myFavorites.insert(roomFavorites)
    }.subscribeOn(Schedulers.io())



    override fun getProductsFromCache(): Single<ProductsLike> = Single.fromCallable {
        val productsLike = ProductsLike()
        db.myFavorites.getAll().map {
            productsLike.add(ProductsItemLikes( it.idf, it.isFavorite))
        }
        productsLike
    }.subscribeOn(Schedulers.io())
}