package com.example.fakestore.di

import androidx.lifecycle.ViewModelProvider
import com.example.fakestore.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}


