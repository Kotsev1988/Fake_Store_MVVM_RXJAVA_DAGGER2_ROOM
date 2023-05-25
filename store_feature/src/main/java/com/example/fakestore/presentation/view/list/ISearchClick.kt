package com.example.fakestore.presentation.view.list

import android.view.View

interface ISearchClick<V: com.example.fakestore.presentation.view.list.IClickView>  {
    var itemClickListener: ((V) -> Unit)?
    var listenerFocusChanged: View.OnFocusChangeListener?
}