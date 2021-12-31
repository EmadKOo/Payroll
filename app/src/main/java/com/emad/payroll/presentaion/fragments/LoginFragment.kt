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
import com.emad.payroll.data.model.requests.LoginRequest
import com.emad.payroll.databinding.FragmentLoginBinding
import com.emad.payroll.presentaion.viewmodels.AuthViewModel
import com.emad.payroll.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : Fragment() {
    val authViewModel: AuthViewModel by activityViewModels()
    lateinit var mBinding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding= FragmentLoginBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login("01068962997",12345678 )
    }

    private fun login(mobileNumber: String, password: Int){
        lifecycleScope.launchWhenStarted {
            authViewModel.login(LoginRequest(mobileNumber, password))
            authViewModel.loginStateFlow.collectLatest {
                when(it){
                    is State.Error -> {
                        Log.d(TAG, "login: ERROR " +it.data)
                    }
                    is State.Loading -> {
                        Log.d(TAG, "login: Loading")
                    }
                    is State.Success -> {
                        Log.d(TAG, "login: SUCCESS " + it.data)
                    }
                }
            }
        }
    }
    companion object {
        private const val TAG = "LoginFragment"
    }
}