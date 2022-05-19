package com.uci.shopapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.uci.shopapp.R
import com.uci.shopapp.data.model.database.entities.ProductEntity
import com.uci.shopapp.databinding.FragmentMyProductsBinding
import com.uci.shopapp.databinding.FragmentProductsBinding
import com.uci.shopapp.databinding.FragmentProfileBinding
import com.uci.shopapp.ui.view.adapters.ProductAdapter

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter
    private val productList = emptyList<ProductEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvProducts.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter(productList)
        binding.rvProducts.adapter = adapter

    }
}