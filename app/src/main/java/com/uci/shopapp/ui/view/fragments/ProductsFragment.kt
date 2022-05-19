package com.uci.shopapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

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
    private val productList = emptyList<ProductEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        productViewModel
        productViewModel.productModel.observe(activity!!, Observer {
            adapter = ProductAdapter(it)
            binding.rvProducts.adapter = adapter
        })
        initRecyclerView()
        loadData()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvProducts.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter(productList)
        binding.rvProducts.adapter = adapter

    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            productViewModel.addDummyProducts()
            productViewModel.getAllProducts()
        }

    }
}