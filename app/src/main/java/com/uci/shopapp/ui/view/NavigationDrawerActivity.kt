package com.uci.shopapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.uci.shopapp.R
import com.uci.shopapp.databinding.ActivityNavigationDrawerBinding
import com.uci.shopapp.ui.view.fragments.MyProductsFragment
import com.uci.shopapp.ui.view.fragments.ProductsFragment
import com.uci.shopapp.ui.view.fragments.ProfileFragment
import com.uci.shopapp.ui.view.fragments.SettingsFragment
import com.uci.shopapp.ui.view_model.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NavigationDrawerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationDrawerBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var toogle: ActionBarDrawerToggle
    private var googleAccount: GoogleSignInAccount? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toogle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )
        googleAccount = intent.extras?.get("account") as GoogleSignInAccount?
        binding.drawerLayout.addDrawerListener(toogle)
        replaceFragment(ProductsFragment(), "Productos")
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener { menuItem ->

            //menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.nav_products -> replaceFragment(ProductsFragment(), "Productos")
                R.id.nav_my_products -> replaceFragment(MyProductsFragment(), "Mis Productos")
                R.id.nav_settings -> replaceFragment(SettingsFragment(), "Configuraciones")
                R.id.nav_profile -> replaceFragment(ProfileFragment(), "Perfil")
                R.id.nav_logoff -> {
                    if (googleAccount != null)
                        googleSignOut()
                        googleRevokeAccess()
                        finish()
                        startActivity(Intent(this,LoginActivity::class.java))
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.frameLayout.id, fragment)
        fragmentTransaction.commit()
        binding.drawerLayout.closeDrawers()
        setTitle(title)
    }

    private fun googleSignOut() {
        userViewModel.getSignInClient().signOut()
            .addOnCompleteListener(this) {
                // ...
            }
    }

    private fun googleRevokeAccess() {
        userViewModel.getSignInClient().revokeAccess()
            .addOnCompleteListener(this) {
                // ...
            }
    }
}