package com.example.fakestore.presentation.adapters.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.presentation.adapters.DelegateAdapter
import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.fakestore.productsEntity.ProductsItem
import com.example.store_feature.databinding.ItemSearchingFragBinding

class SearchDelegateAdapterInFragment() :
    DelegateAdapter<SearchInFragment, SearchDelegateAdapterInFragment.SearchViewHolder>(
        SearchInFragment::class.java) {

    var adapter: SearchingListAdapterInFragment? = null
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SearchViewHolder(ItemSearchingFragBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun bindViewHolder(
        model: SearchInFragment,
        viewHolder: SearchViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {


        when (val payload = payloads.firstOrNull() as? SearchInFragment.ChangePayload) {

            is SearchInFragment.ChangePayload.SearchingChangedTest ->
                viewHolder.bindCategoryChanged(payload.results)

            else -> viewHolder.bind(model)
        }
    }

    inner class SearchViewHolder(
        private val binding: ItemSearchingFragBinding,
    ) : RecyclerView.ViewHolder(binding.root),
        com.example.fakestore.presentation.view.list.ISearchView {

        override var pos: Int  = -1
        fun bind(model: SearchInFragment) {

            adapter = model.presenter?.let { SearchingListAdapterInFragment(it) }

            binding.searchingRecyclerResults.adapter = adapter
            binding.searchingResults.setIconifiedByDefault(false)

            binding.searchingResults.setOnQueryTextListener(object : OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                   model.presenter?.listener?.onQueryTextSubmit(query)

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    model.presenter?.listener?.onQueryTextChange(newText)
                    return true
                }
            })

            binding.searchingResults.setOnCloseListener(model.presenter?.listenerClose)

        }

        fun bindCategoryChanged(result: ArrayList<ProductsItem>) {
                adapter?.notifyDataSetChanged()

        }

    }
}