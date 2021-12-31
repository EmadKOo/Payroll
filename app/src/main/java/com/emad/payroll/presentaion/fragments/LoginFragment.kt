package com.emad.payroll.presentaion.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.emad.payroll.R
import com.emad.payroll.data.model.requests.LoginRequest
import com.emad.payroll.databinding.FragmentLoginBinding
import com.emad.payroll.presentaion.extentions.isEnglish
import com.emad.payroll.presentaion.viewmodels.AuthViewModel
import com.emad.payroll.presentaion.viewmodels.PayrollViewModel
import com.emad.payroll.utils.SharedPreferencesUtils
import com.emad.payroll.utils.State
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    val authViewModel: AuthViewModel by activityViewModels()
    lateinit var mBinding: FragmentLoginBinding
    @Inject
    lateinit var sharedPref: SharedPreferencesUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding= FragmentLoginBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateLogin()
    }

    private fun validateLogin(){
        mBinding.loginBtn.setOnClickListener {
            if (mBinding.phoneNumberET.text.toString().trim().equals("")||mBinding.passwprdET.text.toString().trim().equals("")){
               showSnackBar(getString(R.string.makeSureFields))
            }else{
                login(mBinding.phoneNumberET.text.toString().trim(),mBinding.passwprdET.text.toString().trim().toInt())
            }
        }
    }

    private fun login(mobileNumber: String, password: Int){
        lifecycleScope.launchWhenStarted {
            authViewModel.login(LoginRequest(mobileNumber, password))
            authViewModel.loginStateFlow.collectLatest {
                when(it){
                    is State.Error -> {
                        Log.d(TAG, "login: ERROR " +it.data)
                        showSnackBar(getString(R.string.makeSureFields))
                    }
                    is State.Loading -> {
                        Log.d(TAG, "login: Loading")
                    }
                    is State.Success -> {
                        Log.d(TAG, "login: SUCCESS " + it.data)
                        if (it.data!!.Success){
                            sharedPref.saveToken(it.data.Token)
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToPayrollFragment())
                        } else{
                            if (isEnglish())
                                showSnackBar(it.data.EnglishMessage)
                            else
                                showSnackBar(it.data.ArabicMessage)
                        }
                    }
                }
            }
        }
    }
    private fun showSnackBar(msg: String){
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
    }
    companion object {
        private const val TAG = "LoginFragment"
    }
}