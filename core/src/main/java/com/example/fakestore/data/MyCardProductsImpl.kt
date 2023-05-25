package com.example.fakestore.data



import com.example.fakestore.data.room.Database
import com.example.fakestore.data.room.myCart.entity.RoomMyCard
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.productsEntity.ProductsItem
import com.example.fakestore.productsEntity.Rating
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MyCardProductsImpl(private val db: Database): IMyCardProducts {
    override fun getAllMyCard(): Single<List<ProductsItem>> = Single.fromCallable {
        db.myCardProducts.getAll().map {

            ProductsItem(it.category,
                it.description,
                it.id,
                it.image,
                it.price,
                Rating(0, 0.0),
                it.title,
            it.count)
        }
    }.subscribeOn(Schedulers.io())

    override fun insertProductToMyCard(productsItem: ProductsItem): Single<ProductsItem> = Single.fromCallable {

        val productsRoom =  RoomMyCard(productsItem.id,
                productsItem.category,
                productsItem.description,
                productsItem.image,
                productsItem.price,
                productsItem.title,
            1
        )

        db.myCardProducts.insert(productsRoom)
        productsItem
    }.subscribeOn(Schedulers.io())

    override fun isContain(id: String): Single<Boolean> = Single.fromCallable {
        db.myCardProducts.isContain(id)
    }.subscribeOn(Schedulers.io())

    override fun update(id: String, count: Int): Completable = Completable.fromCallable {
        db.myCardProducts.updateCount(id, count)
    }.subscribeOn(Schedulers.io())

    override fun delete(product: ProductsItem): Completable  = Completable.fromCallable {
        val roomMyCard = RoomMyCard(
            product.id,
            product.category,
            product.description,
            product.image,
            product.price,
            product.title,
            product.count,
        )
        db.myCardProducts.delete(roomMyCard)
    }.subscribeOn(Schedulers.io())



}