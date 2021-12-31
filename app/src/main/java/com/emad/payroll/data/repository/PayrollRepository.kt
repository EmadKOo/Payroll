package com.emad.payroll.data.repository

import com.emad.payroll.data.remote.ApiService
import com.emad.payroll.utils.SharedPreferencesUtils
import javax.inject.Inject

class PayrollRepository @Inject constructor(private val apiService: ApiService) {
    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils
    suspend fun getPayroll()= apiService.getPayroll("Bearer "+ sharedPreferencesUtils.readToken())
}