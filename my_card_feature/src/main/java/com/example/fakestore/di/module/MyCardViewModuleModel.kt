package com.example.fakestore.di.module

import androidx.lifecycle.ViewModel
import com.example.fakestore.di.ViewModelKey
import com.example.fakestore.di.scpoes.MyCardScope
import com.example.fakestore.presentation.viewModel.MyCardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyCardViewModuleModel {

    @MyCardScope
    @Binds
    @IntoMap
    @ViewModelKey(MyCardViewModel::class)
    internal abstract fun bindMyViewModel(viewModel: MyCardViewModel): ViewModel
}