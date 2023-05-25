package com.example.fakestore.presentation.presenter

import com.example.fakestore.presentation.presenter.list.IListProductPresenter
import com.example.fakestore.presentation.view.list.ProductItemView
import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsLike

class ProductsListPresenter() : IListProductPresenter {
    var products = Products()
    var productsLikes = ProductsLike()
    override var onItemClickListener: ((ProductItemView) -> Unit)? = null

    override var onItemClickLikeListener: ((ProductItemView) -> Unit)? = null
    override fun bindView(view: ProductItemView) {

        val product = products[view.pos]
        if (!productsLikes.isEmpty()){
            productsLikes.forEach {
                if (product.id == it.id) {
                    view.setDraw()
                }
            }

        }

        view.setTitle(product.title)
        view.setPrice(product.price.toInt())
        view.setProductImage(product.image)
        view.clickLike()

    }

    override fun getCount(): Int = products.size

}