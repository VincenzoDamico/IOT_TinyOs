package com.example.iot_environmental.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogicViewModel : ViewModel(){
    private val _email = MutableLiveData<String>("")
    val email : LiveData<String> = _email
    private val _password = MutableLiveData<String>("")
    val password : LiveData<String> = _password
    private val _emailError = MutableLiveData<String>("")
    val emailError : LiveData<String> = _emailError
    private val _passwordError = MutableLiveData<String>("")
    val passwordError : LiveData<String> = _passwordError
    private val _passwordVisible = MutableLiveData<Boolean>(false)
    val passwordVisible : LiveData<Boolean> = _passwordVisible

    fun setEmail(email: String){
        _email.value = email
    }
    fun setPassword(password: String){
        _password.value = password
    }
    fun setEmailError(emailError: String){
        _emailError.value = emailError
    }
    fun setPasswordError(passwordError: String){
        _passwordError.value = passwordError
    }
    fun setPasswordVisible(passwordVisible: Boolean){
        _passwordVisible.value = passwordVisible
    }

}