package com.example.fakestore.di.provider

import com.example.fakestore.di.StoreComponent

interface StoreComponentProvider {


    fun getStoreComponent(): StoreComponent
}