package com.uci.shopapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uci.shopapp.R
import com.uci.shopapp.databinding.FragmentMyProductsBinding
import com.uci.shopapp.databinding.FragmentProfileBinding

class MyProductsFragment : Fragment() {
    private var _binding: FragmentMyProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyProductsBinding.inflate(inflater,container,false)
        return binding.root
    }
}