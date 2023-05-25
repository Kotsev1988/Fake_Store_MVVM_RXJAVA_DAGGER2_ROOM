package com.example.fakestore.presentation.presenter

import com.example.fakestore.presentation.presenter.list.IListCategoryPresenter
import com.example.fakestore.presentation.view.list.CategoryItemView
import com.example.fakestore.productsEntity.Categories

class CategoryListPresenter : IListCategoryPresenter {
    var categories = Categories()
    override var itemClickListener: ((CategoryItemView) -> Unit)? = null

    override fun setData(categories: Categories) {
        this.categories = categories
    }

    override fun bindView(view: CategoryItemView) {

        var category = categories[view.pos]

        view.setText(category.name)

        view.loadAvatar(category.name)

        view.clickButton()

        if (category.select ){
            view.changeColor(0)
        }else{
            view.changeColor(1)
        }

    }

    override fun getCount(): Int = categories.size
    override fun clear() = categories.clear()
    override fun changeColorItem(pos: Int) {

    }
}