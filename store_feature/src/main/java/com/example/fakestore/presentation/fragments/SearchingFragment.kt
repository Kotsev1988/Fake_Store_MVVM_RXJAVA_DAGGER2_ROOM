package com.example.fakestore.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.fakestore.di.DaggerStoreComponent
import com.example.fakestore.domain.IGetAllProducts
import com.example.fakestore.presentation.adapters.MainAdapter
import com.example.fakestore.presentation.adapters.search.SearchDelegateAdapterInFragment
import com.example.fakestore.presentation.adapters.search.SearchInFragment
import com.example.fakestore.presentation.presenter.SearchingPresenter
import com.example.fakestore.presentation.view.SearchingView
import com.example.fakestore.productsEntity.ProductsItem
import com.example.fakestore.productsEntity.Rating
import com.example.fakestore.utils.InjectUtils
import com.example.store_feature.databinding.FragmentSearchingBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class SearchingFragment : MvpAppCompatFragment(), SearchingView{

    private var _binding: FragmentSearchingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchingData: IGetAllProducts



    val presenter: SearchingPresenter by moxyPresenter {
        SearchingPresenter(searchingData,  AndroidSchedulers.mainThread())
    }

    private val searchingAdapter by lazy {
        MainAdapter.Builder()
            .add(SearchDelegateAdapterInFragment())
            .build()
    }

    var listDelegateSearch = listOf(
        SearchInFragment(arrayListOf(),  null))


    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerStoreComponent.factory()
            .create(InjectUtils.provideBaseComponent(requireActivity().applicationContext))
            .inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.searchingRecyclerFragment.adapter = searchingAdapter

    }

    override fun init() {

         listDelegateSearch = listOf(
            SearchInFragment(arrayListOf(),  presenter.searchingPresenter))
        searchingAdapter.submitList(listDelegateSearch)

    }

    override fun updateListOnSearching() {

        listDelegateSearch = listOf(
           SearchInFragment(arrayListOf(ProductsItem("", "", 0, "", 0.0, Rating(0,0.0), "" , 0)),  presenter.searchingPresenter),
        )

        searchingAdapter.submitList(listDelegateSearch)
    }

    override fun updateSearchingList() {

        listDelegateSearch = listOf(SearchInFragment(presenter.searchingPresenter.results,  presenter.searchingPresenter))

        searchingAdapter.submitList(listDelegateSearch)
    }

    override fun onError(it: Throwable) {
         TODO("Not yet implemented")
     }

    override fun openFoundedProduct(id: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://fakestore.app/productFragment/$id".toUri())
            .build()

         findNavController().navigate(request)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SearchingFragment()
    }

}