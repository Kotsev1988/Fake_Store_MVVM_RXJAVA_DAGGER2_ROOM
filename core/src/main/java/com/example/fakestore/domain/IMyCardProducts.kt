package com.example.fakestore.domain

import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IMyCardProducts {
    fun getAllMyCard(): Single<List<ProductsItem>>
    fun insertProductToMyCard(productsItem: ProductsItem): Single<ProductsItem>
    fun isContain(id: String): Single<Boolean>
    fun update(id: String, count: Int): Completable
    fun delete(id: ProductsItem): Completable
}