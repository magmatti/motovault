package com.uken.motovault.sign_in.email_sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class EmailSignInViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<EmailAuthState>()
    val authState: LiveData<EmailAuthState> = _authState

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> = _userEmail

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = EmailAuthState.Unauthenticated
            _userEmail.value = null
        } else {
            _authState.value = EmailAuthState.Authenticated
            _userEmail.value = auth.currentUser!!.email
        }
    }

    fun loginWithEmailAndPassword(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = EmailAuthState.Error("Email or password can't be empty!")
            return
        }

        _authState.value = EmailAuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = EmailAuthState.Authenticated
                } else {
                    _authState.value = EmailAuthState.Error(
                        task.exception?.message?:"Something went wrong"
                    )
                }
            }
    }

    fun signUpWithEmailAndPassword(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = EmailAuthState.Error("Email or password can't be empty!")
            return
        }

        _authState.value = EmailAuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _authState.value = EmailAuthState.Authenticated
            } else {
                _authState.value = EmailAuthState.Error(
                    task.exception?.message?:"Something went wrong"
                )
            }
        }
    }

//    fun signOut() {
//        auth.signOut()
//        _authState.value = EmailAuthState.Unauthenticated
//    }
}

sealed class EmailAuthState {
    data object Authenticated: EmailAuthState()
    data object Unauthenticated: EmailAuthState()
    data object Loading: EmailAuthState()
    data class Error(val message: String): EmailAuthState()
}