package com.uci.shopapp.ui.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uci.shopapp.data.model.database.entities.ProductEntity
import com.uci.shopapp.databinding.ItemProductBinding
import com.uci.shopapp.ui.view.ProductDetailsActivity

class ProductViewHolder(view:View):RecyclerView.ViewHolder(view){

    private val binding = ItemProductBinding.bind(view)
    fun bind(product: ProductEntity){
        binding.tvProductName.text = product.name
        binding.tvProductPrice.text = product.price.toString()
        binding.cvElement.setOnClickListener{
            binding.pbarProducts.visibility = View.VISIBLE
            binding.ivIcon.visibility = View.INVISIBLE
            val intent = Intent(binding.root.context,ProductDetailsActivity::class.java)
            intent.putExtra("product_id",product.id)
            binding.root.context.startActivity(intent)
        }
    }
}