package com.example.fakestore.presentation.presenter

import androidx.appcompat.widget.SearchView
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.presentation.adapters.search.SearchListResultPresenterInFragment
import com.example.fakestore.presentation.view.SearchingView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit

class SearchingPresenter(private val searchingData: IGetAllProducts,
                         private val uiScheduler: Scheduler): MvpPresenter<SearchingView>() {



    val searchingPresenter = SearchListResultPresenterInFragment()
    val listProduct = ProductsListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()


    searchingPresenter.listener =
    object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {

            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {

            if (newText != null) {
                viewState.updateListOnSearching()
                if (!newText.isNullOrBlank()){

                searchingData.getAllProducts(newText)
                    .toObservable()
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .distinct()
                    .switchMap { it ->
                        Observable.fromIterable(it)
                            .filter { item ->
                                item.title.contains(newText)
                            }
                            .toList()
                            .toObservable()
                    }
                    .observeOn(uiScheduler)
                    .subscribe(
                        { result ->
                            searchingPresenter.results.clear()
                            searchingPresenter.results.addAll(result)
                            viewState.updateSearchingList()

                        },
                        {
                            viewState.onError(it)

                        }
                    )
            }else{
                    searchingPresenter.results.clear()
                    viewState.updateListOnSearching()
                }

            } else{
                searchingPresenter.results.clear()
                viewState.updateSearchingList()
            }
            return false
        }
    }

    searchingPresenter.listenerClose = SearchView.OnCloseListener{

        searchingPresenter.results.clear()
        false
    }

    searchingPresenter.itemClickListener =
    {
        val id = searchingPresenter.results[it.pos].id

        viewState.openFoundedProduct(id)

       // router.navigateTo(AndroidScreens().product(id.toString()))
    }
}


}