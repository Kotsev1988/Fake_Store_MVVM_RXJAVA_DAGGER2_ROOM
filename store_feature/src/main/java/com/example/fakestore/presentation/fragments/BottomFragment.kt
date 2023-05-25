package com.example.fakestore.presentation.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsItem
import com.example.store_feature.databinding.FragmentBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val  BEST_SELLERS = "BEST_SELLERS"

class BottomFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomBinding? = null
    private val binding get() = _binding!!

    private val  spinner: ArrayList<String> = arrayListOf()
    private  val  spinnerPrice: ArrayList<String> = arrayListOf()
    private  val  spinnerSize: ArrayList<Double> = arrayListOf()

    val bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("BottomFrag")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBottomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfBestSellers = arguments?.let{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList<ProductsItem>(BEST_SELLERS)
            } else {
                it.getParcelableArrayList(BEST_SELLERS)
            }
        }

        initDialog()
        if (listOfBestSellers != null) {
            for (list in listOfBestSellers){

                spinner.add(list.title)
                spinnerSize.add(list.rating.rate)
            }
            spinnerPrice.add("$200 - $400")
            spinnerPrice.add("$100 - $500")
            spinnerPrice.add("$300 - $1000")

        }

        binding.brand.adapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, spinner)
        binding.brand.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (parent != null) {
                    bundle.putString("brand", parent.getItemAtPosition(pos).toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.price.adapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, spinnerPrice)
        binding.price.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (parent != null) {
                    bundle.putString("price", parent.getItemAtPosition(pos).toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.size.adapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, spinnerSize)
        binding.size.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                if (parent != null) {
                    bundle.putString("size", parent.getItemAtPosition(pos).toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.closebottomfragment.setOnClickListener {
            this.dismiss()
        }

        binding.materialButton.setOnClickListener{
            parentFragmentManager.setFragmentResult(KAY_PARENT, bundle)

        }

    }

    private fun initDialog() {
        requireDialog().window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requireDialog().window?.statusBarColor = requireContext().getColor(android.R.color.transparent)

    }

    companion object {

        @JvmStatic
        fun newInstance(productsItem: Products) = BottomFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(BEST_SELLERS, productsItem)
            }
        }
    }

}