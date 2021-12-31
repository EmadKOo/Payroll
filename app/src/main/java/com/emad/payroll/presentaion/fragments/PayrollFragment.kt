package com.emad.payroll.presentaion.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.emad.payroll.R
import com.emad.payroll.databinding.FragmentPayrollBinding
import com.emad.payroll.presentaion.viewmodels.PayrollViewModel
import com.emad.payroll.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PayrollFragment : Fragment() {
    private lateinit var mBinding: FragmentPayrollBinding
    val payrollViewModel: PayrollViewModel by activityViewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding= FragmentPayrollBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPayroll()
    }

    private fun getPayroll(){
        lifecycleScope.launchWhenStarted {
            payrollViewModel.getPayroll()
            payrollViewModel.payrollStateFlow.collectLatest {
                when(it){
                    is State.Error -> {
                        Log.d(TAG, "getPayroll: ERROR " +it.data)
                    }
                    is State.Loading -> {
                        Log.d(TAG, "getPayroll: Loading")
                    }
                    is State.Success -> {
                        Log.d(TAG, "getPayroll: SUCCESS " +it.data)
                    }
                }
            }
        }
    }
    companion object {
        private const val TAG = "PayrollFragment"
    }
}