package com.emad.payroll.data.usecases.payroll

import com.emad.payroll.data.model.Payroll

interface GetPayrollUsecase {
    suspend operator fun invoke(): Payroll
}