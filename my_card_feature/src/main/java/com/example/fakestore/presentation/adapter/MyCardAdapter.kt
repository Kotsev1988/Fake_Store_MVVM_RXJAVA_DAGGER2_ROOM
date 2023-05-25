package com.example.fakestore.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.image.ILoadImage
import com.example.fakestore.presentation.presenter.list.IListMyProducts
import com.example.fakestore.presentation.view.IMyProductsView
import com.example.product_feature.databinding.ItemMyCartBinding
import javax.inject.Inject

class MyCardAdapter(var presenter: IListMyProducts): RecyclerView.Adapter<MyCardAdapter.MyProductsViewHolder>() {


    @Inject
    lateinit var imageLoader: ILoadImage<ImageView>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductsViewHolder {
        return MyProductsViewHolder(ItemMyCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: MyProductsViewHolder, position: Int) {
        presenter.bindView(holder.apply {
            pos = position
        })
    }

    override fun getItemCount(): Int = presenter.getCount()

    fun setData(presenterNew: IListMyProducts){
        presenter = presenterNew
        notifyDataSetChanged()
    }


    inner class MyProductsViewHolder(private val binding: ItemMyCartBinding): RecyclerView.ViewHolder(binding.root),
        IMyProductsView {


        override fun setText(text: String) {

            binding.productName.text = text
        }

        override fun setPrice(price: String) {
            binding.productPrice.text = price
        }

        override fun loadAvatar(url: String) {
            imageLoader.loadImage(url, binding.product)
        }

        override fun setCounter(counter: String) {
            binding.score1.text = counter
            binding.decreaseTeam1.setOnClickListener {
                presenter.itemClickListenerDecrease?.invoke(this)
            }

            binding.increaseTeam1.setOnClickListener {
                presenter.itemClickListenerIncrease?.invoke(this)
            }

            binding.deleteFromMyCard.setOnClickListener{
                presenter.itemClickListenerDelete?.invoke(this)
            }
        }


        override var pos: Int = -1
    }
}