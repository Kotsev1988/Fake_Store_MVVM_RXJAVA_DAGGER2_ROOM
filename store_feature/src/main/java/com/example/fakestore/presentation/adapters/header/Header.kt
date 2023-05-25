package com.example.fakestore.presentation.adapters.header

import com.example.fakestore.presentation.adapters.DelegateAdapterItem

class Header(val header: String): DelegateAdapterItem {
    override fun id(): Any = Header::class.java

    override fun content(): Any = header

    override fun payload(other: Any): DelegateAdapterItem.Payloadable {
        if (other is Header){
            if (header != other.header){
                return ChangePayload.CategoryNameChanged(other.header)
            }

        }
        return DelegateAdapterItem.Payloadable.None
    }

    sealed class ChangePayload: DelegateAdapterItem.Payloadable {
        data class CategoryNameChanged (val categoryName: String): ChangePayload()
    }
}