package com.emad.payroll.data.remote

import com.emad.payroll.data.model.Employer
import com.emad.payroll.data.model.Payroll
import com.emad.payroll.data.model.requests.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("LogIn")
    suspend fun login(@Body loginRequest: LoginRequest): Employer

    @GET("GetPayroll")
    suspend fun getPayroll(@Header("Authorization") token: String): Payroll
}