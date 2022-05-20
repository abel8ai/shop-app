package com.uci.shopapp.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.uci.shopapp.R
import com.uci.shopapp.databinding.ActivityProductDetailsBinding
import com.uci.shopapp.ui.view_model.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProductDetailsBinding
    private val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val productId = intent.extras!!.getInt("product_id")
        productViewModel.productModel.observe(this, Observer {
            binding.tvProductName.text = it.name
            binding.tvProductDescription.text = it.description
            binding.btnBuy.setOnClickListener {
                confirmPurchaseDialog()
            }
        })
        CoroutineScope(Dispatchers.IO).launch {
            productViewModel.getProductById(productId)
        }
    }
    private fun confirmPurchaseDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.confirm_purchase_title))
        builder.setMessage(R.string.confirm_purchase)
        builder.setPositiveButton(R.string.accept){_,_->
            Toast.makeText(this, R.string.purchase_notification, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,NavigationDrawerActivity::class.java))
            this.finish()
        }
        builder.setNegativeButton(R.string.cancel){dialog,_->
            dialog.dismiss()
        }
        builder.create()
        builder.show()
    }
}