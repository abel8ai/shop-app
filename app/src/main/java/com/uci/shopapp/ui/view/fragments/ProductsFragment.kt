package com.uci.shopapp.ui.view.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.uci.shopapp.data.model.database.entities.ProductEntity
import com.uci.shopapp.databinding.FragmentProductsBinding
import com.uci.shopapp.ui.view.adapters.ProductAdapter
import com.uci.shopapp.ui.view_model.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var adapter: ProductAdapter
    private var productList = mutableListOf<ProductEntity>()
    private var sectionProductList = mutableListOf<ProductEntity>()
    var isScrolling = false
    var currentItems: Int = 0
    var scrolledItems: Int = 0
    var totalItems: Int = 0
    var lastitem: Int = 0
    private lateinit var manager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        productViewModel
        productViewModel.productsModel.observe(activity!!, Observer {
            productList = it
            for (i in 0..9) {
                sectionProductList.add(it[i])
            }
            adapter = ProductAdapter(sectionProductList)
            binding.rvProducts.adapter = adapter
        })
        initRecyclerView()
        loadData()
        return binding.root
    }

    private fun initRecyclerView() {
        manager = GridLayoutManager(context,2)
        binding.rvProducts.layoutManager = manager
        adapter = ProductAdapter(productList)
        binding.rvProducts.adapter = adapter
        binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager.childCount
                totalItems = manager.itemCount
                scrolledItems = manager.findFirstVisibleItemPosition()
                if (isScrolling && currentItems + scrolledItems == totalItems && sectionProductList.size<productList.size) {
                    isScrolling = false
                    getMoreData()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }
        })
    }

    private fun getMoreData() {
        val lastElement = sectionProductList.get(sectionProductList.size - 1).id
        if(lastElement!! < productList.size-1) {
            sectionProductList.add(productList.get(lastElement))
            sectionProductList.add(productList.get(lastElement + 1))
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            productViewModel.addDummyProducts()
            productViewModel.getAllProducts()
        }

    }

}