package com.uci.shopapp.ui.view_model

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.uci.shopapp.data.model.database.ShopDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val signInClient: GoogleSignInClient) : ViewModel() {

    fun getSignInClient() = signInClient

    fun doDummyLogin(user: String, password: String):Boolean{
        return (user == "peter" && password == "123")

    }


}
