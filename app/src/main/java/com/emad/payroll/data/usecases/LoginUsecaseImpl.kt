package com.emad.payroll.data.usecases

import com.emad.payroll.data.model.Employer
import com.emad.payroll.data.model.requests.LoginRequest
import com.emad.payroll.data.repository.AuthRepository
import javax.inject.Inject

class LoginUsecaseImpl @Inject constructor(private val authRepository: AuthRepository): LoginUsecase {
    override suspend fun invoke(loginRequest: LoginRequest): Employer = authRepository.login(loginRequest)
}