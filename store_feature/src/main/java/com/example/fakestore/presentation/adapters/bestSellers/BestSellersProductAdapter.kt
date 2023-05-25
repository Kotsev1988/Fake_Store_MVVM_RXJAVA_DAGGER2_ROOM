package com.example.fakestore.presentation.adapters.bestSellers

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.image.ILoadImage
import com.example.fakestore.presentation.presenter.list.IListProductPresenter
import com.example.fakestore.presentation.view.list.ProductItemView
import com.example.store_feature.R
import com.example.store_feature.databinding.ItemProductsBinding
import javax.inject.Inject

class BestSellersProductAdapter(private val presenter: IListProductPresenter) :
    RecyclerView.Adapter<BestSellersProductAdapter.BestSellersProductViewHolder>() {

    @Inject
    lateinit var imageLoader: ILoadImage<ImageView>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BestSellersProductViewHolder {
        return BestSellersProductViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)).apply {
            itemView.setOnClickListener {
                presenter.onItemClickListener?.invoke(this)
            }
        }
    }

    override fun onBindViewHolder(holder: BestSellersProductViewHolder, position: Int) {


        presenter.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class BestSellersProductViewHolder(val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root),
        ProductItemView {
        override var pos: Int = -1

        override fun setTitle(title: String) {
            binding.techsNameModel.text = title
        }

        override fun setPrice(price: Int) {
            binding.price.text = price.toString()
        }

        override fun setProductImage(url: String) {
            imageLoader.loadImage(url, binding.techImage)
        }

        override fun clickLike() {

            binding.like.setOnClickListener {
                presenter.onItemClickLikeListener?.invoke(this)
                //binding.like.setImageDrawable( AppCompatResources.getDrawable(itemView.context, R.drawable.ic_liked))

            }

        }

        override fun setDraw() {
            println("SetDraw")
            binding.like.setImageDrawable( AppCompatResources.getDrawable(itemView.context, R.drawable.ic_liked))
        }
    }
}