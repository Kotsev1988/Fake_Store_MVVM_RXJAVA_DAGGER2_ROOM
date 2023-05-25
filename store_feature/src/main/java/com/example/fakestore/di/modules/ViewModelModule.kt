package com.example.fakestore.di.modules

import androidx.lifecycle.ViewModel
import com.example.fakestore.di.ViewModelKey
import com.example.fakestore.di.scopes.StoreScope
import com.example.fakestore.presentation.viewModel.StoreViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @StoreScope
    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    internal abstract fun bindMyViewModel(viewModel: StoreViewModel): ViewModel
}