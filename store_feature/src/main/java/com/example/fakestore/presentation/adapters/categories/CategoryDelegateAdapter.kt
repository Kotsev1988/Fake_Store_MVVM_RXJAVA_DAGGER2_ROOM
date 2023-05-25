package com.example.fakestore.presentation.adapters.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.di.DaggerStoreComponent
import com.example.fakestore.presentation.adapters.DelegateAdapter
import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.fakestore.productsEntity.Categories
import com.example.fakestore.utils.InjectUtils
import com.example.store_feature.databinding.ItemCategoryBinding

class CategoryDelegateAdapter() :
   DelegateAdapter<Category, CategoryDelegateAdapter.CategoryViewHolder>(Category::class.java) {

    private var categoryHorizontalAdapter: CategoryHorizontalAdapter? = null

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun bindViewHolder(
        model: Category,
        viewHolder: CategoryViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {

        when (val payload = payloads.firstOrNull() as? Category.ChangePayload) {

            is Category.ChangePayload.CategoryNameChanged ->
                viewHolder.bindCategoryChanged(payload.categoryName)

            else -> viewHolder.bind(model)
        }
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Category) {


            categoryHorizontalAdapter = model.presenter?.let {
                CategoryHorizontalAdapter(it).apply {
                    DaggerStoreComponent.factory().create(InjectUtils.provideBaseComponent(itemView.context.applicationContext)).inject(this)
//                        .builder()
//                        .baseComponent(InjectUtils.provideBaseComponent(itemView.context.applicationContext))
//                        .build()
//                        .inject(this)
                    //App.instance.appComponent.inject(this)
                }
            }
            binding.recyclerView2.adapter = categoryHorizontalAdapter

        }

        fun bindCategoryChanged(payloads: Categories) {

        }
    }


}