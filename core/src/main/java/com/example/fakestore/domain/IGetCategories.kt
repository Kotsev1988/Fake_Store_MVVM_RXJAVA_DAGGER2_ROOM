package com.example.fakestore.domain

import com.example.fakestore.productsEntity.Categories
import io.reactivex.rxjava3.core.Single

interface IGetCategories {
    fun getCategories(): Single<Categories>
}