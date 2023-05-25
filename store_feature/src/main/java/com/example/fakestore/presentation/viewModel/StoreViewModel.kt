package com.example.fakestore.presentation.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.data.room.cache.IFavoritesCache
import com.example.fakestore.domain.IGetCategories
import com.example.fakestore.domain.IGetProducts
import com.example.fakestore.presentation.adapters.locationAndFilter.FilterClick
import com.example.fakestore.presentation.adapters.search.SearchClick
import com.example.fakestore.presentation.presenter.CategoryListPresenter
import com.example.fakestore.presentation.presenter.ProductsListPresenter
import com.example.fakestore.presentation.viewModel.appState.AppState
import com.example.fakestore.productsEntity.Category
import com.example.fakestore.productsEntity.Products
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.IOException
import java.util.Scanner
import javax.inject.Inject

private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class StoreViewModel @Inject constructor(
    var productList: IGetProducts,
    var categoryList: IGetCategories,
    var favoriteList: IFavoritesCache,
) : ViewModel() {

    private val listCategory = CategoryListPresenter()
    var listProduct = ProductsListPresenter()

    val searchClick = SearchClick()

    private val filterClick = FilterClick()

    var productsFilter = Products()

    private val products = Products()

    var clickedCategory: String = ""
    var city = ""

    private val _lists: MutableLiveData<AppState> = MutableLiveData()
    val listItem: LiveData<AppState>
        get() {
            return _lists
        }

    private val _clickEvent: MutableLiveData<Event<String>> = MutableLiveData()
    val clickEvent: LiveData<Event<String>>
        get() {
            return _clickEvent
        }

    @SuppressLint("CheckResult")
    fun init() {

        listCategory.itemClickListener = {

            val index = it.pos

            listCategory.categories[index].select = true
            listCategory.categories.indices.forEach {
                if (it != index) {
                    listCategory.categories[it].select = false
                }
            }

            clickedCategory = listCategory.categories[index].name
            loadProductByClick(clickedCategory)
        }

        listProduct.onItemClickListener = {

            _clickEvent.value = Event(listProduct.products[it.pos].id.toString())
        }

        listProduct.onItemClickLikeListener = {

            favoriteList.insertProductsToDB(listProduct.products[it.pos].id, true)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    favoriteList.getProductsFromCache().observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                listProduct.productsLikes.clear()
                                listProduct.productsLikes.addAll(it)

                                _lists.value = AppState.updateLikes(
                                    listCategory.categories,
                                    listProduct.products,
                                    it,
                                    listCategory,
                                    listProduct,
                                    filterClick
                                )
                            },
                            {
                                _lists.value = it.message?.let { it1 -> AppState.Error(it1) }
                            }
                        )
                }

        }

        searchClick.listenerFocusChanged = object : View.OnFocusChangeListener {
            override fun onFocusChange(p0: View?, p1: Boolean) {
                if (p1) {
                    _lists.value = AppState.NavigateToSearch
                    // viewState.navigateToSearchFragment()
                    // router.navigateTo(AndroidScreens().search())
                } else {
                    return
                }
            }
        }

        filterClick.itemClickListener = {

            _lists.value = AppState.ShowBottomDialog
        }

        loadCategories()
    }


    @SuppressLint("CheckResult")
    private fun loadProductByClick(category: String) {

        productList.getProductByCategory(category).observeOn(AndroidSchedulers.mainThread())
            .subscribe(

                { products ->
                    listProduct.products.clear()
                    listProduct.products.addAll(products)
                    productsFilter.clear()
                    productsFilter.addAll(products)
                    _lists.value =
                        AppState.SetProducts(
                            city,
                            listCategory.categories,
                            products,
                            listCategory,
                            listProduct,
                            filterClick
                        )
                },
                {
                    _lists.value = it.message?.let { it1 -> AppState.Error(it1) }
                }
            )
    }

    @SuppressLint("CheckResult")
    private fun loadCategories() {
        categoryList.getCategories().observeOn(AndroidSchedulers.mainThread()).subscribe(

            { categories ->
                listCategory.categories.clear()
                listCategory.categories.addAll(categories as List<Category>)

                if (clickedCategory.isNullOrBlank()) {
                    clickedCategory = listCategory.categories[0].name
                    loadProducts(clickedCategory)
                } else {
                    loadProducts(clickedCategory)
                }

            },
            {
                _lists.value = it.message?.let { it1 -> AppState.Error(it1) }
            }
        )
    }

    @SuppressLint("CheckResult")
    private fun loadProducts(category: String) {

        productList.getProductByCategory(category).observeOn(AndroidSchedulers.mainThread())
            .subscribe(

                { products ->
                    listProduct.products.clear()
                    listProduct.products.addAll(products)
                    productsFilter.clear()
                    productsFilter.addAll(products)

                    favoriteList.getProductsFromCache().observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                listProduct.productsLikes.addAll(it)
                                _lists.value = AppState.SetProducts(
                                    city,
                                    listCategory.categories,
                                    products,
                                    listCategory,
                                    listProduct,
                                    filterClick
                                )
                            },
                            {
                                _lists.value = it.message?.let { it1 -> AppState.Error(it1) }
                            }
                        )
                },
                {
                    _lists.value = it.message?.let { it1 -> AppState.Error(it1) }
                }
            )
    }


    @SuppressLint("CheckResult")
    fun filter(brand: String?, price: String?, result: String?) {

        val getInts = Scanner(price).useDelimiter("[^\\d]+")
        val firstPrice: Int = getInts.nextInt()
        val secondPrice: Int = getInts.nextInt()

        if (brand != null) {
            if (price != null) {
                if (result != null) {
                    Observable.fromIterable(productsFilter)
                        .filter {
                            it.title == brand

                                    && it.price >= firstPrice
                                    && it.price <= secondPrice
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toList()
                        .subscribe({
                            listProduct.products.clear()
                            listProduct.products.addAll(it)

                            products.clear()
                            products.addAll(it)

                            _lists.value =
                                AppState.SetFilter(
                                    listCategory.categories,
                                    products,
                                    listCategory,
                                    listProduct,
                                    filterClick
                                )
                        }, {
                            _lists.value = it.message?.let { it1 -> AppState.Error(it1) }
                        })
                }
            }
        }
    }

    private lateinit var contextLocation: Context


    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: android.location.Location) {


            getAddressAsync(contextLocation, location)

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }
    }

    fun getLocation(context: Context) {

        context.let { context ->

            contextLocation = context
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {

                val locationManager = context.getSystemService(Context.LOCATION_SERVICE)
                        as LocationManager
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    val provider =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            onLocationListener
                        )
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        //liveDataLocation.value = AppStateLocation.EmptyData("Empty")
                    } else {
                        getAddressAsync(context, location)
                    }
                }
            } else {
                //liveDataLocation.value = AppStateLocation.ShowRationalDialog("show")
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun getAddressAsync(context: Context, location: android.location.Location) {

        val geoCoder = Geocoder(context)


        Completable.fromCallable {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                city = addresses?.get(0)?.getAddressLine(0).toString()

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _lists.value = AppState.SetLocation(
                    city,
                    listCategory.categories,
                    listProduct.products,
                    listCategory,
                    listProduct,
                    filterClick
                )

            }, {
                _lists.value = it.message?.let { it1 -> AppState.Error(it1) }
            })
    }
}