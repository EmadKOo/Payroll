package com.emad.payroll.domain.di

import com.emad.payroll.data.repository.AuthRepository
import com.emad.payroll.data.usecases.LoginUsecase
import com.emad.payroll.data.usecases.LoginUsecaseImpl
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
}