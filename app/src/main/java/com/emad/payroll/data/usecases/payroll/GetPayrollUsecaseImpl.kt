package com.emad.payroll.data.usecases.payroll

import com.emad.payroll.data.model.Payroll
import com.emad.payroll.data.repository.PayrollRepository
import javax.inject.Inject

class GetPayrollUsecaseImpl @Inject constructor(private val payrollRepository: PayrollRepository): GetPayrollUsecase {
    override suspend fun invoke(): Payroll = payrollRepository.getPayroll()
}