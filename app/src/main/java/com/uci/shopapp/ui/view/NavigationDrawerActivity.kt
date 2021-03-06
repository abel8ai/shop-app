package com.uci.shopapp.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.uci.shopapp.R
import com.uci.shopapp.databinding.ActivityNavigationDrawerBinding
import com.uci.shopapp.ui.view.fragments.MyProductsFragment
import com.uci.shopapp.ui.view.fragments.ProductsFragment
import com.uci.shopapp.ui.view.fragments.ProfileFragment
import com.uci.shopapp.ui.view.fragments.SettingsFragment
import com.uci.shopapp.ui.view_model.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject


@AndroidEntryPoint
class NavigationDrawerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationDrawerBinding
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var toogle: ActionBarDrawerToggle
    private var googleAccount: GoogleSignInAccount? = null
    private var facebookToken: AccessToken? = null
    private lateinit var context: Context

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
        context = this
        googleAccount = intent.extras?.get("google_account") as GoogleSignInAccount?
        facebookToken = intent.extras?.get("facebook_token") as AccessToken?
        binding.drawerLayout.addDrawerListener(toogle)
        val signedUser =
            binding.navView.getHeaderView(0).findViewById(R.id.tv_signed_user) as TextView
        if (googleAccount != null) {
            signedUser.text = googleAccount!!.givenName
        } else if (facebookToken != null) {
            val request = GraphRequest.newMeRequest(facebookToken) { `object`, response ->
                signedUser.text = response?.jsonObject?.getString("name")
            }
            request.executeAsync();
        } else
            signedUser.text = "peter"


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
                    if (googleAccount != null) {
                        googleSignOut()
                        googleRevokeAccess()
                    } else if (facebookToken != null) {
                        GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/me/permissions/",
                            null,
                            HttpMethod.DELETE,
                            {
                                LoginManager.getInstance().logOut()
                                startActivity(Intent(context, LoginActivity::class.java))
                                this.finish()
                            }).executeAsync()
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                        this.finish()
                    }
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
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
            }
    }

    private fun googleRevokeAccess() {
        userViewModel.getSignInClient().revokeAccess()
            .addOnCompleteListener(this) {
                // ...
            }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.exit_title))
        builder.setMessage(R.string.confirm_exit)
        builder.setPositiveButton(R.string.accept) { _, _ ->
            finishAffinity()
        }
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create()
        builder.show()
    }
}