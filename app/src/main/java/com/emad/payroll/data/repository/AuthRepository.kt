package com.emad.payroll.data.repository

import com.emad.payroll.data.model.requests.LoginRequest
import com.emad.payroll.data.remote.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService){
    suspend fun login(loginRequest: LoginRequest)= apiService.login(loginRequest)
}