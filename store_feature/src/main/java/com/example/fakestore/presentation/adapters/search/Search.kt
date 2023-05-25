package com.example.fakestore.presentation.adapters.search

import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.fakestore.presentation.view.list.IListSearchClick
import com.example.fakestore.productsEntity.ProductsItem

class Search(var results: ArrayList<ProductsItem>, val presenter: IListSearchClick?): DelegateAdapterItem {

    override fun id(): Any = Search::class.java

    override fun content(): Any = results

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is Search){
            if (results != other.results){
                return ChangePayload.SearchingChanged(other.results)
            }

        }
        return DelegateAdapterItem.Payloadable.None
    }



    inner class SearchingContent(private val results: ArrayList<ProductsItem>, val presenter: IListSearchClick?){
        override fun equals(other: Any?): Boolean {
            if (other is SearchingContent){
              return results == other.results && presenter == other.presenter
            }
            return false
        }
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class SearchingChanged (var results: ArrayList<ProductsItem>): ChangePayload()
    }
}