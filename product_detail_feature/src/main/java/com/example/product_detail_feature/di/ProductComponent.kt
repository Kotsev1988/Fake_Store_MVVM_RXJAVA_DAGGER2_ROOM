package com.example.product_detail_feature.di

import com.example.fakestore.di.BaseComponent
import com.example.fakestore.di.ViewModelFactoryModule
import com.example.product_detail_feature.di.modules.ProductModule
import com.example.product_detail_feature.di.modules.ProductViewModelModule
import com.example.product_detail_feature.di.scopes.ProductScope
import com.example.product_detail_feature.presentation.ProductFragment
import dagger.Component

@ProductScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [ProductModule::class, ViewModelFactoryModule::class, ProductViewModelModule::class]
)
interface ProductComponent {

    fun inject(productFragment: ProductFragment)


}