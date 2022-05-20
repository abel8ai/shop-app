package com.uci.shopapp.ui.view_model

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val signInClient: GoogleSignInClient,
                                        private val googleSignInAccount: GoogleSignInAccount?) :
    ViewModel() {

    fun getSignInClient() = signInClient

    fun getSignedInAccount() = googleSignInAccount
}
