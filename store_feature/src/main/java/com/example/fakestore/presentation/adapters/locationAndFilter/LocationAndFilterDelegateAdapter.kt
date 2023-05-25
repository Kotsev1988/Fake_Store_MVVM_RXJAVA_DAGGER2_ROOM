package com.example.fakestore.presentation.adapters.locationAndFilter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.presentation.adapters.DelegateAdapter
import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.fakestore.presentation.view.list.IFilterClickView
import com.example.store_feature.databinding.LocationAndFilterBinding
import com.google.android.material.R

class LocationAndFilterDelegateAdapter() :
    DelegateAdapter<LocationAndFilter, LocationAndFilterDelegateAdapter.LocationAndFilterViewHolder>(
        LocationAndFilter::class.java) {


    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        LocationAndFilterViewHolder(LocationAndFilterBinding.inflate(LayoutInflater.from(parent.context),
    parent,
    false))

    override fun bindViewHolder(
        model: LocationAndFilter,
        viewHolder: LocationAndFilterViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {

        when (val payload = payloads.firstOrNull() as? LocationAndFilter.ChangePayload) {

            is LocationAndFilter.ChangePayload.LocationNameChanged ->
                viewHolder.bindChange(payload.locationName)

            else -> viewHolder.bind(model)
        }

        viewHolder.bind(model)
    }

    inner class LocationAndFilterViewHolder(private val binding: LocationAndFilterBinding) :
        RecyclerView.ViewHolder(binding.root),
        IFilterClickView {
        fun bind(model: LocationAndFilter) {
            val  spinner: ArrayList<String> = arrayListOf()
            spinner.add(model.location)

            binding.location.adapter = ArrayAdapter(binding.location.context, R.layout.support_simple_spinner_dropdown_item, spinner)

            binding.filter.setOnClickListener(model.presenter?.itemClickListener)
        }

        fun bindChange(locationName: String){
            val  spinner: ArrayList<String> = arrayListOf()
            spinner.add(locationName)

            binding.location.adapter = ArrayAdapter(binding.location.context, R.layout.support_simple_spinner_dropdown_item, spinner)

        }
    }
}