package com.example.fakestore.presentation.adapters.locationAndFilter

import android.view.View
import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.fakestore.presentation.view.list.IFilterClick

data class LocationAndFilter(

    val location: String,
    val presenter: IFilterClick<View>?

): DelegateAdapterItem {

    override fun id(): Any = LocationAndFilter::class.toString()

    override fun content(): Any = location

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is LocationAndFilter){
            if (location != other.location){
                return ChangePayload.LocationNameChanged(other.location)
            }

        }
        return DelegateAdapterItem.Payloadable.None
    }

//    inner class LocationContent(val location: String){
//        override fun equals(other: Any?): Boolean {
//            if (other is LocationContent){
//                return location == other.location
//            }
//            return false
//        }
//    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class LocationNameChanged (val locationName: String): ChangePayload()
    }
}