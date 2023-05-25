package com.example.fakestore.di

import com.example.fakestore.di.module.MyCardModule
import com.example.fakestore.di.module.MyCardViewModuleModel
import com.example.fakestore.di.scpoes.MyCardScope
import com.example.fakestore.presentation.adapter.MyCardAdapter
import com.example.fakestore.presentation.fragment.MyCartFragment
import dagger.Component

@MyCardScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [MyCardModule::class, ViewModelFactoryModule::class, MyCardViewModuleModel::class]
)
interface MyCardComponent {

    fun inject(myCartFragment: MyCartFragment)
    fun inject(myCardAdapter: MyCardAdapter)
}