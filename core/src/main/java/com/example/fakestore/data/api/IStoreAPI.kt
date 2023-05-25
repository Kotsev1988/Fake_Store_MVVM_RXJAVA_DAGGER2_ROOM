package com.example.fakestore.data.api

import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IStoreAPI {

    @GET("products/categories")
    fun getAllCategories(): Single<List<String>>

    @GET("/products/category/{category}")
    fun getProductByCategory(@Path("category") category: String): Single<Products>

    @GET("/products/{id}")
    fun getProductById(@Path("id")id: String): Single<ProductsItem>

    @GET("/products")
     fun getAllProducts(): Single<List<ProductsItem>>


}