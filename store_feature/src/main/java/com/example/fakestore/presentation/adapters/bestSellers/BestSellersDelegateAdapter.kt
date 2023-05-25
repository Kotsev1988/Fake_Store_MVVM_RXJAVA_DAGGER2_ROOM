package com.example.fakestore.presentation.adapters.bestSellers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.di.DaggerStoreComponent
import com.example.fakestore.presentation.adapters.DelegateAdapter
import com.example.fakestore.presentation.adapters.DelegateAdapterItem
import com.example.fakestore.productsEntity.ProductsLike
import com.example.fakestore.utils.InjectUtils
import com.example.store_feature.databinding.ItemBestSellersBinding

class BestSellersDelegateAdapter :
    DelegateAdapter<BestSellers, BestSellersDelegateAdapter.BestSellersViewHolder>(BestSellers::class.java) {

    private var bestSellersProductAdapter: BestSellersProductAdapter? = null

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        BestSellersViewHolder(ItemBestSellersBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun bindViewHolder(
        model: BestSellers,
        viewHolder: BestSellersViewHolder,
        payloads: List<DelegateAdapterItem.Payloadable>,
    ) {
        when (val payload = payloads.firstOrNull() as? BestSellers.ChangePayload) {

            is BestSellers.ChangePayload.UBestSellersLikes ->
                viewHolder.bindCategoryChanged(payload.productLikes)

            else -> viewHolder.bind(model)
        }
        viewHolder.bind(model)
    }

    inner class BestSellersViewHolder(private val binding: ItemBestSellersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: BestSellers) {

            bestSellersProductAdapter = model.presenter?.let {
                BestSellersProductAdapter(it).apply {
                    DaggerStoreComponent.factory().create(InjectUtils.provideBaseComponent(itemView.context.applicationContext)).inject(this)
                }
            }
            binding.hotsalesRecycle.adapter = bestSellersProductAdapter
        }

        fun bindCategoryChanged(newProducts: ProductsLike) {
            println("bindCategoryChanged "+newProducts.toString())
           // bestSellersProductAdapter?.notifyDataSetChanged()
        }


    }
}