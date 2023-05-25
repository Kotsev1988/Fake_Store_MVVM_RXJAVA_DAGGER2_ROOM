package com.example.fakestore.presentation.view.list

interface CategoryItemView: IItemView {
    fun clickButton()
    fun setText(text: String)
    fun loadAvatar(url: String)
     fun changeColor(pos: Int)


}