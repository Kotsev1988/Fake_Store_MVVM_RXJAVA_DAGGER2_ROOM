package com.example.fakestore.di

import com.example.fakestore.di.scope.MainActivityScope
import com.example.fakestore.presentation.activity.MainActivity
import dagger.Component

@MainActivityScope
@Component(
    dependencies = [BaseComponent::class],
    modules = [
        AppModule::class,
       // NavModule::class
       // CiceroneModule::class
       // CiceroneModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)


//    fun inject(searchingFragment: SearchingFragment)
//    fun inject(bestSellersProductAdapter: BestSellersProductAdapter)
//    fun inject(categoryHorizontalAdapter: CategoryHorizontalAdapter)
//    fun inject(myProductAdapter: MyProductsAdapter)
}