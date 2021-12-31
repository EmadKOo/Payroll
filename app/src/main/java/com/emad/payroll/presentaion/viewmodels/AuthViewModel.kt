package com.emad.payroll.presentaion.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emad.payroll.data.model.Employer
import com.emad.payroll.data.model.requests.LoginRequest
import com.emad.payroll.data.usecases.LoginUsecase
import com.emad.payroll.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val loginUsecase: LoginUsecase): ViewModel() {

    private val _loginStateFlow= MutableStateFlow<State<Employer>>(State.Init())
    val loginStateFlow= _loginStateFlow.asStateFlow()

    fun login(loginRequest: LoginRequest)= viewModelScope.launch {
        try {
            _loginStateFlow.emit(State.Loading())
            _loginStateFlow.emit(State.Success(loginUsecase.invoke(loginRequest)))
        }catch (ex: Exception){
            _loginStateFlow.emit(State.Error(ex.localizedMessage))
            Log.d(TAG, "login: " + ex.cause)
            Log.d(TAG, "login: " + ex.message)
            Log.d(TAG, "login: " + ex.stackTrace)
            Log.d(TAG, "login: " + ex.localizedMessage)
        }
    }
companion object{
    private const val TAG = "AuthViewModel"
}
}