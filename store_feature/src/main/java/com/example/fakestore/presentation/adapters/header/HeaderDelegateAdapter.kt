package com.example.fakestore.presentation.adapters.header

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.presentation.adapters.DelegateAdapter
import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.store_feature.databinding.HeaderItemBinding

class HeaderDelegateAdapter(): DelegateAdapter<Header, HeaderDelegateAdapter.HeaderViewHolder>(
    Header::class.java){

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        HeaderViewHolder(HeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun bindViewHolder(
        model: Header,
        viewHolder: HeaderViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {
        viewHolder.bind(model)
    }

    inner class HeaderViewHolder(
        private val binding: HeaderItemBinding,
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(model: Header) {
            binding.header.text = model.header
        }
    }
}