package com.example.fakestore.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.fakestore.di.DaggerMyCardComponent
import com.example.fakestore.presentation.adapter.MyCardAdapter
import com.example.fakestore.presentation.viewModel.MyCardViewModel
import com.example.fakestore.presentation.viewModel.appState.MyCardAppState
import com.example.fakestore.utils.InjectUtils
import com.example.product_feature.databinding.FragmentMyCartBinding
import moxy.MvpAppCompatFragment
import javax.inject.Inject

class MyCartFragment : MvpAppCompatFragment() {

    private var _binding: FragmentMyCartBinding? = null
    private val binding get() = _binding!!


    private var myAdapter: MyCardAdapter? = null


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MyCardViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MyCardViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        DaggerMyCardComponent
            .builder()
            .baseComponent(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMyCartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.listItem.observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.onFirstViewAttach()

        binding.backFromMyCard.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    private fun renderData(it: MyCardAppState) {

        when(it){
            is MyCardAppState.OnSuccess ->{

                println("MyCard "+it.myCardProducts.toString())

                myAdapter = MyCardAdapter(it.productsListPresenter).apply {
                    DaggerMyCardComponent.builder().baseComponent(context?.let {
                        InjectUtils.provideBaseComponent(it.applicationContext)
                    }).build().inject(this)
                }


                binding.myShops.adapter = myAdapter

            }

            is MyCardAppState.OnUpdate ->{
                myAdapter?.setData(it.productsListPresenter)
            }

            is MyCardAppState.Error ->{
                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }
            is MyCardAppState.TotalPrice ->{
                binding.totalPrice.text = it.totalPrice
            }
        }
    }

}