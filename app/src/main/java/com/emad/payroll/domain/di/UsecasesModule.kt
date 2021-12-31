package com.emad.payroll.domain.di

import com.emad.payroll.data.repository.AuthRepository
import com.emad.payroll.data.repository.PayrollRepository
import com.emad.payroll.data.usecases.login.LoginUsecase
import com.emad.payroll.data.usecases.login.LoginUsecaseImpl
import com.emad.payroll.data.usecases.payroll.GetPayrollUsecase
import com.emad.payroll.data.usecases.payroll.GetPayrollUsecaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UsecasesModule {
    @Singleton
    @Provides
    fun provideLoginUsecase(authRepository: AuthRepository): LoginUsecase =
        LoginUsecaseImpl(authRepository)

    @Singleton
    @Provides
    fun provideGettingPayrollUsecase(payrollRepository: PayrollRepository): GetPayrollUsecase =
        GetPayrollUsecaseImpl(payrollRepository)

}