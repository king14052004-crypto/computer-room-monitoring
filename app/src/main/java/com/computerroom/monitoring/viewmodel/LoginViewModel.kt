package com.computerroom.monitoring.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Vui lòng nhập email và mật khẩu"
            return
        }

        _isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginResult.value = true
                } else {
                    _errorMessage.value = task.exception?.message ?: "Đăng nhập thất bại"
                }
            }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Vui lòng nhập email và mật khẩu"
            return
        }

        if (password.length < 6) {
            _errorMessage.value = "Mật khẩu phải có ít nhất 6 ký tự"
            return
        }

        _isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginResult.value = true
                } else {
                    _errorMessage.value = task.exception?.message ?: "Đăng ký thất bại"
                }
            }
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
