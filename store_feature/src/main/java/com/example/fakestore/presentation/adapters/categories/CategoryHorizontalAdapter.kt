package com.example.fakestore.presentation.adapters.categories

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.image.ILoadImage
import com.example.fakestore.presentation.presenter.list.IListCategoryPresenter
import com.example.fakestore.presentation.view.list.CategoryItemView
import com.example.store_feature.R
import com.example.store_feature.databinding.ItemCategoryNameBinding
import javax.inject.Inject

class CategoryHorizontalAdapter(private val presenter: IListCategoryPresenter) :
    RecyclerView.Adapter<CategoryHorizontalAdapter.CategoryHorizontalViewHolder>() {

    @Inject
    lateinit var imageLoader: ILoadImage<ImageView>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryHorizontalViewHolder {
        return CategoryHorizontalViewHolder(ItemCategoryNameBinding.inflate(LayoutInflater.from(
            parent.context), parent, false))
            .apply {
                itemView.setOnClickListener {
                    presenter.itemClickListener?.invoke(this)
                }
            }
    }

    override fun onBindViewHolder(holder: CategoryHorizontalViewHolder, position: Int) {

        presenter.bindView(holder.apply {
            pos = position
        })


    }

    override fun getItemCount(): Int = presenter.getCount()

    inner class CategoryHorizontalViewHolder(private val binding: ItemCategoryNameBinding) :
        RecyclerView.ViewHolder(binding.root),
        CategoryItemView {

        override fun clickButton() {
            binding.Phones.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
                notifyDataSetChanged()
            }

        }

        override fun setText(text: String) {
            binding.phonetext.text = text
        }

        override fun loadAvatar(url: String) {

            if (url == "jewelery"){
                imageLoader.loadImage(R.drawable.jewelery, binding.Phones)

            }
            else if(url == "electronics") {
                imageLoader.loadImage(R.drawable.electronics, binding.Phones)
            }
            else if(url == "men's clothing") {
                imageLoader.loadImage(R.drawable.menclothes, binding.Phones)
            }
            else if(url == "women's clothing") {
                imageLoader.loadImage(R.drawable.womenclothes, binding.Phones)
            }
            else
            {
                binding.Phones.setImageResource(R.drawable.ic_launcher_foreground)
            }

        }
        override fun changeColor(pos: Int) {

            if (pos == 0){
                binding.Phones.setBackgroundColor(Color.parseColor("#FF6E4E"))
            }else{
                binding.Phones.setBackgroundColor(Color.parseColor("#F6F4F4"))
            }
        }

        override var pos: Int = -1
    }
}