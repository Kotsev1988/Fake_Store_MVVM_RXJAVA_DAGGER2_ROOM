package com.example.fakestore.presentation.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakestore.domain.IMyCardProducts
import com.example.fakestore.presentation.presenter.MyCardProductsPresenter
import com.example.fakestore.presentation.viewModel.appState.MyCardAppState
import com.example.fakestore.productsEntity.ProductsItem
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MyCardViewModel @Inject constructor(
    private val myCard: IMyCardProducts
): ViewModel() {


    private val myCartListProducts = MyCardProductsPresenter()
    private val prices : HashMap<Int, Int> = hashMapOf()

    private val _lists: MutableLiveData<MyCardAppState> = MutableLiveData()
    val listItem: LiveData<MyCardAppState>
        get() {
            return _lists
        }


    @SuppressLint("CheckResult")
    fun onFirstViewAttach() {

        init()
        myCartListProducts.itemClickListenerIncrease = {

            val key = myCartListProducts.myProducts[it.pos].id
            val defaultValue = myCartListProducts.myProducts[it.pos].count

            prices[key] = prices.getOrDefault(key, defaultValue)+1
            prices[key]?.let { it1 ->
                myCard.update(key.toString(), it1).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    myCard.getAllMyCard()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            myCartListProducts.myProducts.clear()
                            myCartListProducts.myProducts.addAll(it)
                            _lists.value = MyCardAppState.OnSuccess(it, myCartListProducts)

                            totalPrice(it)
                        },
                            {
                                _lists.value = it.message?.let { it1 -> MyCardAppState.Error(it1) }

                            })
                },{
                    _lists.value = it.message?.let { it1 -> MyCardAppState.Error(it1) }
                })
            }

        }

        myCartListProducts.itemClickListenerDecrease = {
            val key = myCartListProducts.myProducts[it.pos].id
            val defaultValue = myCartListProducts.myProducts[it.pos].count
            prices[key] = prices.getOrDefault(key, defaultValue)-1

            prices[key]?.let { it1 ->
                myCard.update(key.toString(), it1).observeOn(AndroidSchedulers.mainThread()).subscribe({
                    myCard.getAllMyCard()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            myCartListProducts.myProducts.clear()
                            myCartListProducts.myProducts.addAll(it)
                            _lists.value = MyCardAppState.OnUpdate(myCartListProducts)

                            totalPrice(it)
                        },
                            {

                                _lists.value = it.message?.let { it1 -> MyCardAppState.Error(it1) }
                            })
                },{
                    _lists.value = it.message?.let { it1 -> MyCardAppState.Error(it1) }
                })
            }
        }

        myCartListProducts.itemClickListenerDelete = {
            val product = myCartListProducts.myProducts[it.pos]
            myCard.delete(product).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    myCartListProducts.myProducts.remove(product)
                    _lists.value = MyCardAppState.OnUpdate(myCartListProducts)
                    totalPrice(myCartListProducts.myProducts)


                },{
                    _lists.value = it.message?.let { it1 -> MyCardAppState.Error(it1) }
                })

        }
    }

    @SuppressLint("CheckResult")
    fun init(){
        myCard.getAllMyCard()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                myCartListProducts.myProducts.clear()
                myCartListProducts.myProducts.addAll(it)
                _lists.value = MyCardAppState.OnSuccess(it, myCartListProducts)

                totalPrice(it)
            },
                {
                    _lists.value = it.message?.let { it1 -> MyCardAppState.Error(it1) }

                })
    }

    private fun totalPrice(myProducts: List<ProductsItem>){
        var totalPrice = 0
        myProducts.forEach {
            totalPrice += it.price.toInt()*it.count
        }
        _lists.value = MyCardAppState.TotalPrice(totalPrice.toString())

    }

}