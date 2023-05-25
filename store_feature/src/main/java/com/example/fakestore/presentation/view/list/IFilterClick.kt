package com.example.fakestore.presentation.view.list

import android.view.View

interface IFilterClick<V: View>  {

    var itemClickListener: ((V) -> Unit)?

}