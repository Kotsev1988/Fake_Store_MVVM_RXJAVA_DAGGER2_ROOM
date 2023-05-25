package com.example.fakestore.di

import android.content.Context
import com.example.fakestore.data.api.IStoreAPI
import com.example.fakestore.network.AndroidNetworkStatus
import com.example.fakestore.network.INetworkStates
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Named("baseURL")
    @Provides
    fun baseURL(): String = "https://fakestoreapi.com/"

    @Singleton
    @Provides
    fun api(@Named("baseURL") baseURL: String, gson: Gson): IStoreAPI =
        Retrofit.Builder().baseUrl(baseURL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IStoreAPI::class.java)

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun networkStatus(context: Context): INetworkStates = AndroidNetworkStatus(context)
}