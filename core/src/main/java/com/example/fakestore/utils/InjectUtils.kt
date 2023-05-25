package com.example.fakestore.utils

import android.content.Context
import com.example.fakestore.di.BaseComponent
import com.example.fakestore.di.BaseComponentProvider

object InjectUtils {

    fun provideBaseComponent(applicationContext: Context): BaseComponent {
        return if (applicationContext is BaseComponentProvider) {
            (applicationContext as BaseComponentProvider).provideBaseComponent()
        } else {
            throw IllegalStateException("Provide the application context which implement BaseComponentProvider")
        }
    }



}