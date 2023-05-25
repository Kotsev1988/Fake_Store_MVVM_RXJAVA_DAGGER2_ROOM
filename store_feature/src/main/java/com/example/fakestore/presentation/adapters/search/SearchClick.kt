package com.example.fakestore.presentation.adapters.search

import android.view.View
import com.example.fakestore.presentation.view.list.IClickView
import com.example.fakestore.presentation.view.list.IListSearchClick


class SearchClick() : IListSearchClick {

    override var itemClickListener: ((IClickView) -> Unit)? = null
    override var listenerFocusChanged: View.OnFocusChangeListener? = null
}