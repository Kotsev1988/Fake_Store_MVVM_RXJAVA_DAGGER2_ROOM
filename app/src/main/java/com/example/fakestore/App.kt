package com.example.fakestore

import android.app.Application
import android.content.Context
import com.example.fakestore.di.AppComponent
import com.example.fakestore.di.AppModule
import com.example.fakestore.di.BaseComponent
import com.example.fakestore.di.BaseComponentProvider
import com.example.fakestore.di.DaggerAppComponent
import com.example.fakestore.di.DaggerBaseComponent
import com.example.fakestore.utils.InjectUtils


class App : Application(), BaseComponentProvider {

    companion object {
        lateinit var instance: App
    }

    private lateinit var baseComponent: BaseComponent

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        baseComponent = DaggerBaseComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        baseComponent.inject(this)

        appComponent = DaggerAppComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(this))
            .appModule(AppModule(this))
            .build()
    }

    fun getAppContext(): Context {
        return instance.applicationContext
    }

    override fun provideBaseComponent(): BaseComponent = baseComponent

}