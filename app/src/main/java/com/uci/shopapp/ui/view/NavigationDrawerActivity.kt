package com.uci.shopapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.uci.shopapp.R
import com.uci.shopapp.databinding.ActivityNavigationDrawerBinding
import com.uci.shopapp.ui.view.fragments.MyProductsFragment
import com.uci.shopapp.ui.view.fragments.ProductsFragment
import com.uci.shopapp.ui.view.fragments.ProfileFragment
import com.uci.shopapp.ui.view.fragments.SettingsFragment

class NavigationDrawerActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNavigationDrawerBinding
    private lateinit var toogle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toogle = ActionBarDrawerToggle(this, binding.drawerLayout,R.string.drawer_open, R.string.drawer_close)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {menuItem->

            //menuItem.isChecked = true
            when(menuItem.itemId){
                R.id.nav_products -> replaceFragment(ProductsFragment(),"Productos")
                R.id.nav_my_products -> replaceFragment(MyProductsFragment(),"Mis Productos")
                R.id.nav_settings -> replaceFragment(SettingsFragment(),"Configuraciones")
                R.id.nav_profile -> replaceFragment(ProfileFragment(),"Perfil")
                //R.id.nav_logoff -> TODO: Close session
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun replaceFragment(fragment: Fragment, title: String){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id,fragment)
        fragmentTransaction.commit()
        binding.drawerLayout.closeDrawers()
        setTitle(title)
    }
}