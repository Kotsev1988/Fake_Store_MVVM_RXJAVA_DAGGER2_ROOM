package com.example.product_detail_feature.di.modules

import androidx.lifecycle.ViewModel
import com.example.fakestore.di.ViewModelKey
import com.example.product_detail_feature.di.scopes.ProductScope
import com.example.product_detail_feature.presentation.viewModel.ProductDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProductViewModelModule {
    @ProductScope
    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    internal abstract fun bindMyViewModel(viewModel: ProductDetailViewModel): ViewModel
}