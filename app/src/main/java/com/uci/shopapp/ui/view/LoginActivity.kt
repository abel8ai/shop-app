package com.uci.shopapp.ui.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.uci.shopapp.R
import com.uci.shopapp.databinding.ActivityLoginBinding
import com.uci.shopapp.ui.view_model.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val RC_SIGN_IN: Int = 44556
    private var facebookToken: AccessToken? = null
    private lateinit var context: Context
    private lateinit var callbackManager: CallbackManager
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        val account = GoogleSignIn.getLastSignedInAccount(this)
        startWithGoogle(account)
        facebookToken = AccessToken.getCurrentAccessToken()
        startWithfacebook(facebookToken)
        callbackManager = CallbackManager.Factory.create()
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, NavigationDrawerActivity::class.java)
            startActivity(intent)
        }
        binding.llLoginForm.setOnClickListener {
            Toast.makeText(context, R.string.forgot_pass_error, Toast.LENGTH_SHORT).show()
        }
        binding.btnSignInGoogle.setSize(SignInButton.SIZE_WIDE)
        binding.btnSignInGoogle.setOnClickListener {
            signIn()
        }
        binding.btnSignInFacebook.setReadPermissions(Arrays.asList("email","name", "public_profile"))
        binding.btnSignInFacebook.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {

                override fun onCancel() {
                    // App code
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(context, R.string.facebook_login_error, Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(result: LoginResult?) {
                    facebookToken = result?.accessToken
                    startWithfacebook(facebookToken)
                }
            })
    }

    private fun signIn() {
        val signInIntent = userViewModel.getSignInClient().signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            startWithGoogle(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            startWithGoogle(null)
        }
    }

    private fun startWithGoogle(account: GoogleSignInAccount?) {
        if (account != null) {
            val intent = Intent(this, NavigationDrawerActivity::class.java)
            intent.putExtra("google_account", account)
            startActivity(intent)
        }
    }
    private fun startWithfacebook(token: AccessToken?) {
        if (token != null) {
            val intent = Intent(this, NavigationDrawerActivity::class.java)
            intent.putExtra("facebook_token", token)
            startActivity(intent)
        }
    }
}