package com.example.fakestore.data.room.cache.room


import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.ICategoriesCache
import com.example.fakestore.data.room.categories.entity.RoomCategories
import com.example.fakestore.productsEntity.Categories
import com.example.fakestore.productsEntity.Category
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoriesCache(private val db: Database): ICategoriesCache {
    override fun insertCategoriesToDB(categories: Categories): Single<Categories> =
        Single.fromCallable {
            val roomCategories = categories.map { category ->
                RoomCategories(category = category.name, false)
            }
            db.categoriesDao.insert(roomCategories)
            categories
        }.subscribeOn(Schedulers.io())


    override fun getCategoriesFromCache(): Single<Categories> = Single.fromCallable{
        val categories = Categories()
        db.categoriesDao.getAll().map {
            categories.add(Category(it.category, it.select))
        }
        categories
    }

}