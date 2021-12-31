package com.emad.payroll.data.usecases

import com.emad.payroll.data.model.Employer
import com.emad.payroll.data.model.requests.LoginRequest

interface LoginUsecase {
    suspend operator fun invoke(loginRequest: LoginRequest): Employer
}