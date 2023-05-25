package com.example.fakestore.di

import com.example.fakestore.di.modules.StoreModule
import com.example.fakestore.di.scopes.StoreScope
import com.example.fakestore.di.modules.ViewModelModule
import com.example.fakestore.presentation.adapters.bestSellers.BestSellersProductAdapter
import com.example.fakestore.presentation.adapters.categories.CategoryHorizontalAdapter
import com.example.fakestore.presentation.fragments.SearchingFragment
import com.example.fakestore.presentation.fragments.StoreFragment
import dagger.Component

@StoreScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [StoreModule::class,  ViewModelFactoryModule::class, ViewModelModule::class]
)
interface StoreComponent {


    @Component.Factory
    interface Factory{
        fun create (baseComponent: BaseComponent): StoreComponent
    }

    fun inject(storeFragment: StoreFragment)

    fun inject(searchingFragment: SearchingFragment)
    fun inject(bestSellersProductAdapter: BestSellersProductAdapter)
    fun inject(categoryHorizontalAdapter: CategoryHorizontalAdapter)


}