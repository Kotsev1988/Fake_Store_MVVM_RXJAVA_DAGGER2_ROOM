package com.example.product_detail_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.fakestore.utils.InjectUtils
import com.example.product_detail_feature.R
import com.example.product_detail_feature.databinding.FragmentProductBinding
import com.example.product_detail_feature.di.DaggerProductComponent
import com.example.product_detail_feature.presentation.viewModel.ProductDetailViewModel
import com.example.product_detail_feature.presentation.viewModel.appState.ProductAppState
import moxy.MvpAppCompatFragment
import javax.inject.Inject

class ProductFragment : MvpAppCompatFragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ProductDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProductDetailViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerProductComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .build().inject(this)

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("metadataFileSyncFilter")

        viewModel.init(id)


        viewModel.productItem.observe(viewLifecycleOwner) {
            renderData(it)
        }

        binding.addToCart.setOnClickListener {
            viewModel.addToCard()
        }

        binding.backfromdetails.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun renderData(it: ProductAppState) {

        when (it) {
            is ProductAppState.OnSuccess -> {
                setTitle(it.bestSellers.title)

                 setImage(it.bestSellers.image)

                 setDescription(it.bestSellers.description)

                 setPrice(it.bestSellers.price.toString())

            }

            is ProductAppState.IsContain -> {
                if (it.isContain) {

                    binding.addToCart.visibility = View.GONE
                    binding.existInMyCard.visibility = View.VISIBLE
                }else{
                    binding.addToCart.visibility = View.VISIBLE
                    binding.existInMyCard.visibility = View.GONE
                }
            }

            is ProductAppState.AddToCard -> {

                binding.addToCart.visibility = View.GONE
                binding.existInMyCard.visibility = View.VISIBLE
                Toast.makeText(requireActivity(), "Successfully added", Toast.LENGTH_SHORT).show()
            }

            is ProductAppState.Error -> {

                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setTitle(text: String) {

        binding.modelName.text = text
    }

     private fun setImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(binding.modelImage)
    }

     private fun setDescription(description: String) {
        binding.description.text = description
    }

     private fun setPrice(price: String) {
        binding.addToCart.text = getString(R.string.add_to_card) + " " + price + "$"
    }


}