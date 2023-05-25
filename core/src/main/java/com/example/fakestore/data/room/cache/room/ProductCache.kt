package com.example.fakestore.data.room.cache.room

import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.cache.IProductCache
import com.example.fakestore.data.room.products.entity.RoomProducts
import com.example.fakestore.productsEntity.ProductsItem
import com.example.fakestore.productsEntity.Rating
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ProductCache(private val db: Database): IProductCache {
    override fun insertProductsToDB(products: ProductsItem): Single<ProductsItem> =

        Single.fromCallable {

            val productsRoom =
                RoomProducts(products.id,
                    products.category,
                    products.description,
                    products.image,
                    products.price,
                    products.title,
                false
                    )

            db.productsDao.insert(productsRoom)
            products
        }.subscribeOn(Schedulers.io())



    override fun getProductsFromCache(id: String): Single<ProductsItem>  =
        Single.fromCallable {
            val roomProduct = db.productsDao.getProductById(id)

                ProductsItem(roomProduct.category,
                    roomProduct.description,
                    roomProduct.id,
                    roomProduct.image,
                    roomProduct.price,
                    Rating(0, 0.0),
                    roomProduct.title,
                    0
                )

        }.subscribeOn(Schedulers.io())


}