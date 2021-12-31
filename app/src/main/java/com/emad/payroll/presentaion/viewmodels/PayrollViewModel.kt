package com.emad.payroll.presentaion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emad.payroll.data.model.Payroll
import com.emad.payroll.data.repository.PayrollRepository
import com.emad.payroll.data.usecases.payroll.GetPayrollUsecase
import com.emad.payroll.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PayrollViewModel @Inject constructor(private val getPayrollUsecase: GetPayrollUsecase): ViewModel() {

    private val _payrollStateFlow= MutableStateFlow<State<Payroll>>(State.Init())
    val payrollStateFlow= _payrollStateFlow.asStateFlow()

    fun getPayroll()= viewModelScope.launch {
        try {
            _payrollStateFlow.emit(State.Loading())
            _payrollStateFlow.emit(State.Success(getPayrollUsecase.invoke()))
        }catch (ex: Exception){
            _payrollStateFlow.emit(State.Error(ex.localizedMessage))
        }
    }
}