package com.example.fakestore.productsEntity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductsItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String,
    val count: Int
) : Parcelable