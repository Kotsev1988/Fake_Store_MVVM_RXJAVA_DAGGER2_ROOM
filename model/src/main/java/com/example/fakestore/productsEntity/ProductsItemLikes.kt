package com.example.fakestore.productsEntity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductsItemLikes (
    val id: Int,
    val isFavorite: Boolean
        ):Parcelable