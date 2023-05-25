package com.example.fakestore.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.example.fakestore.di.DaggerStoreComponent
import com.example.fakestore.presentation.adapters.MainAdapter
import com.example.fakestore.presentation.adapters.bestSellers.BestSellers
import com.example.fakestore.presentation.adapters.bestSellers.BestSellersDelegateAdapter
import com.example.fakestore.presentation.adapters.categories.Category
import com.example.fakestore.presentation.adapters.categories.CategoryDelegateAdapter
import com.example.fakestore.presentation.adapters.header.Header
import com.example.fakestore.presentation.adapters.header.HeaderDelegateAdapter
import com.example.fakestore.presentation.adapters.locationAndFilter.LocationAndFilter
import com.example.fakestore.presentation.adapters.locationAndFilter.LocationAndFilterDelegateAdapter
import com.example.fakestore.presentation.adapters.search.Search
import com.example.fakestore.presentation.adapters.search.SearchClick
import com.example.fakestore.presentation.adapters.search.SearchDelegateAdapter
import com.example.fakestore.presentation.viewModel.StoreViewModel
import com.example.fakestore.presentation.viewModel.appState.AppState
import com.example.fakestore.productsEntity.Categories
import com.example.fakestore.productsEntity.Products
import com.example.fakestore.productsEntity.ProductsLike
import com.example.fakestore.utils.InjectUtils
import com.example.store_feature.R
import com.example.store_feature.databinding.FragmentStoreBinding
import moxy.MvpAppCompatFragment
import javax.inject.Inject

const val KAY_PARENT = "key_parent"
const val REQUEST_CODE = 30

class StoreFragment : MvpAppCompatFragment() {

    private var _binding: FragmentStoreBinding? = null
    private val binding get() = _binding!!

    private val mainAdapter by lazy {
        MainAdapter.Builder()
            .add(LocationAndFilterDelegateAdapter())
            .add(HeaderDelegateAdapter())
            .add(CategoryDelegateAdapter())
            .add(SearchDelegateAdapter(viewModel.searchClick))
            .add(HeaderDelegateAdapter())
            .add(BestSellersDelegateAdapter())
            .build()
    }

    private val headerCategory: Header = Header("Select Category")
    private val headerProduct: Header = Header("BestSellers")

    private var nav: NavController? = null


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: StoreViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[StoreViewModel::class.java]
    }


    private var listDelegates =
        listOf(
            LocationAndFilter("", null), headerProduct, Category(Categories(), null),
            Search(arrayListOf(), null),
            headerCategory,
            BestSellers(Products(), ProductsLike(), null)
        )

    var productsFilter = Products()
    private var location: String = ""
    private val likes = ProductsLike()

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

        println("onCreateView ")

        _binding = FragmentStoreBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()

        viewModel.listItem.observe(viewLifecycleOwner) {
            renderData(it)
        }

        viewModel.clickEvent.observe(viewLifecycleOwner) {

            val id = it.goToProductDetailifNotHandled()

            id?.let {
                val bundle = Bundle()

                bundle.putString("PRODUCT_ID", id)

                val request = NavDeepLinkRequest.Builder
                    .fromUri("android-app://fakestore.app/productFragment/${id}".toUri())
                    .build()
                nav?.navigate(request)
            }

        }

        checkPermission()

        binding.frameLoad.visibility = View.VISIBLE

        nav = findNavController()
        binding.recyclerView.adapter = mainAdapter
    }

    private fun renderData(it: AppState) {
        when (it) {
            is AppState.OnSuccess -> {
                println("OnSuccess "+it.bestSellers)
                productsFilter.clear()

                productsFilter = it.bestSellers as Products
                likes.addAll(it.productsListPresenter.productsLikes)


                listDelegates = listOf(
                    LocationAndFilter(it.city, it.filterClick),
                    headerProduct,
                    Category(it.category, it.categoryListPresenter),
                    Search(arrayListOf(), SearchClick()),
                    headerCategory,
                    BestSellers(
                        it.bestSellers,
                        ProductsLike(),
                        it.productsListPresenter
                    )
                )

                mainAdapter.submitList(listDelegates)
                binding.frameLoad.visibility = View.GONE

            }


            is AppState.OnSearch -> {

            }

            is AppState.SetProducts -> {
                productsFilter.clear()
                productsFilter = it.bestSellers as Products

                listDelegates = listOf(
                    LocationAndFilter(it.city, it.filterClick),
                    headerProduct,
                    Category(it.categoryListPresenter.categories, it.categoryListPresenter),
                    Search(arrayListOf(), SearchClick()),
                    headerCategory,
                    BestSellers(
                        it.bestSellers,
                        ProductsLike(),
                        it.productsListPresenter
                    )
                )

                mainAdapter.submitList(listDelegates)
                binding.frameLoad.visibility = View.GONE
            }

            is AppState.updateLikes -> {

                likes.clear()
                likes.addAll(it.productsLike)

                listDelegates = listOf(
                    LocationAndFilter(location, it.filterClick),
                    headerProduct,
                    Category(Categories(), it.categoryListPresenter),
                    Search(arrayListOf(), SearchClick()),
                    headerCategory,
                    BestSellers(it.bestSellers as Products, likes, it.productsListPresenter)
                )

                mainAdapter.submitList(listDelegates)
            }

            is AppState.SetLocation ->{
                location = it.city
        println("location "+location)
        mainAdapter.submitList(
            listOf(
                LocationAndFilter(location, it.filterClick),
                headerProduct,
                Category(Categories(), it.categoryListPresenter),
                Search(arrayListOf(), SearchClick()),
                headerCategory,
                BestSellers(Products(), ProductsLike(), it.productsListPresenter)
            )
        )
            }

            is AppState.ShowBottomDialog -> {

                showBottomDialog(productsFilter)
            }

            is AppState.SetFilter -> {
                listDelegates = listOf(
                    LocationAndFilter("", it.filterClick),
                    headerProduct,
                    Category(it.categoryListPresenter.categories, it.categoryListPresenter),
                    Search(arrayListOf(), SearchClick()),
                    headerCategory,
                    BestSellers(
                        it.bestSellers,
                        ProductsLike(),
                        it.productsListPresenter
                    )
                )

                mainAdapter.submitList(listDelegates)
            }

            is AppState.NavigateToSearch -> {
                navigateToSearchFragment()
            }

            is AppState.Error -> {
                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }

            is AppState.Loading -> {


            }
        }
    }


    private lateinit var bottomDialog: BottomFragment

    private fun showBottomDialog(productsFilter: Products) {

        bottomDialog = BottomFragment.newInstance(productsFilter)
        bottomDialog.show(this.childFragmentManager, "tag")

        childFragmentManager.setFragmentResultListener(KAY_PARENT, this) { _, result ->
            viewModel.filter(
                result.getString("brand"),
                result.getString("price"),
                result.getString("size")
            )
            bottomDialog.dismiss()
        }
    }

//    override fun setLocation(city: String) {
//        location = city
//        println("location")
//        mainAdapter.submitList(
//            listOf(
//                LocationAndFilter(city, presenter.filterClick),
//                headerProduct,
//                Category(Categories(), presenter.listCategory),
//                Search(arrayListOf(), presenter.searchClick),
//                headerCategory,
//                BestSellers(Products(), ProductsLike(), presenter.listProduct)
//            )
//        )
//    }


    fun navigateToSearchFragment() {
        nav?.navigate(R.id.search_navigation)

    }

    private fun checkPermission() {
        requireActivity().let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    viewModel.getLocation(requireActivity())
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }

                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermission = 0;
                if (grantResults.isNotEmpty()) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermission++
                        }
                    }
                    if (grantResults.size == grantedPermission) {
                        // presenter.getLocation(requireActivity())
                    } else {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
            }
        }
    }

    private fun showDialog(tile: String, message: String) {

        requireContext().let {
            AlertDialog.Builder(it)
                .setTitle(tile)
                .setMessage(message)
                .setNegativeButton(R.string.dialog_button_close) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun showRationaleDialog() {
        requireContext().let {
            AlertDialog.Builder(it)
                .setTitle(R.string.dialog_rationale_title)
                .setMessage(R.string.dialog_rationale_meaasge)
                .setPositiveButton(it.getString(R.string.dialog_rationale_give_access))
                { _, _ ->
                    requestPermission()
                }
                .setNegativeButton(R.string.dialog_rationale_decline) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}