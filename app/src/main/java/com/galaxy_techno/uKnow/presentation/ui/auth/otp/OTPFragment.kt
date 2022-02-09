package com.galaxy_techno.uKnow.presentation.ui.auth.otp

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.common.InternetChecker
import com.galaxy_techno.uKnow.common.NetworkStatus
import com.galaxy_techno.uKnow.databinding.FragmentOtpBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.galaxy_techno.uKnow.presentation.extension.*
import com.galaxy_techno.uKnow.presentation.single_activity.MainActivity
import com.galaxy_techno.uKnow.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class OTPFragment : AuthFragment<FragmentOtpBinding>(FragmentOtpBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()

    @Inject
    lateinit var internetChecker: InternetChecker
    private var internetStatus: Boolean = false

    override fun setupView() {
        super.setupView()
        clearErrorOnFocus()
    }

    override fun setupListener() {
        super.setupListener()

        binding.btnCode.setOnClickListener {

            if (!internetStatus) {
                binding.root.displaySnack(getString(R.string.snack_no_internet))
            } else {
                viewModel.setPhoneNumber(
                    binding.etPhone.text?.trim().toString()
                )
                viewModel.triggerOTP(
                    binding.etPhone.text?.trim().toString()
                )
                viewModel.setOTPLoadingState(true)
            }
        }

        binding.btnVerify.setOnClickListener {

            if (validate()) {
                if (!internetStatus) {
                    binding.root.displaySnack(getString(R.string.snack_no_internet))
                } else {
                    viewModel.triggerValidate(
                        binding.etPhone.text?.trim().toString(),
                        binding.etCode.text?.trim().toString()
                    )
                    viewModel.setVerifyingLoadingState(true)
                }
            }
        }

        binding.etPhone.addTextChangedListener {
            binding.tilPhone.setCloseable(it)
            it?.let { text ->
                binding.btnCode.isVisible = text.isVerifiedPhone()
            }
        }
    }

    override fun observe() {
        super.observe()

        internetChecker.observe(viewLifecycleOwner) {
            internetStatus = when (it) {
                NetworkStatus.Available -> {
                    true
                }
                NetworkStatus.UnAvailable -> {
                    false
                }
                else -> false
            }
        }

        viewModel.phoneNumber.observe(viewLifecycleOwner) {
            binding.etPhone.setText(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // Use StateFlow with collectLatest EVER
            viewModel.isVerifyingLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_verify_otp))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.timer.collectLatest {
                checkOTPButtonState(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            // Use Events with collect
            viewModel.otpEvent.collectLatest {
                when (it) {
                    is RemoteEvent.LoadingEvent -> {
                        viewModel.setOTPLoadingState(true)
                    }
                    is RemoteEvent.ErrorEvent -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_otp_fail)
                        )
                    }
                    is RemoteEvent.FailEvent -> {
                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is RemoteEvent.SuccessEvent -> {

                        val response = it.data?.data!!
                        if (response.isRegister) {
                            navigateToSheet()
                        } else {
                            binding.root.displaySnack(getString(R.string.snack_request_otp_success))
                            viewModel.setTimer(it.data.data.expireTimeMin)
                            val code = it.data.data.otpCode
                            delay(3000L)
                            binding.etCode.setText(code)
                        }

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.validateEvent.collectLatest {
                when (it) {
                    is RemoteEvent.LoadingEvent -> {
                        viewModel.setVerifyingLoadingState(true)
                    }
                    is RemoteEvent.ErrorEvent -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_otp_verify_fail)
                        )
                    }
                    is RemoteEvent.FailEvent -> {
                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is RemoteEvent.SuccessEvent -> {
                        binding.root.displaySnack(getString(R.string.snack_request_otp_verify_success))
                        binding.btnVerify.setActive(false)
                        delay(500L)
                        navigateToRegister()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.isOTPLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_get_otp))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }
    }

    private fun navigateToRegister() {
        val directions =
            OTPFragmentDirections.actionOtpToRegister(binding.etPhone.text?.trim().toString())
        findNavController().navigate(directions)

    }

    private fun navigateToSheet() {
        val directions =
            OTPFragmentDirections.actionOtpToSheet(binding.etPhone.text?.trim().toString())
        findNavController().navigate(directions)
    }

    private fun checkOTPButtonState(count: Int) {
        binding.btnCode.setActive(count == 0)
        binding.etPhone.setActive(count == 0)
        binding.tilPhone.setCloseable(null)
        if (count != 0) {
            binding.btnCode.text = count.toString()
        } else binding.btnCode.text = getString(R.string.btn_otp)
    }

    private fun validate(): Boolean {
        if (!binding.etPhone.isVerifiedPhone()) {
            binding.tilPhone.error = getString(R.string.error_register_phone)
            return false
        }
        if (!binding.etCode.isVerifiedName()) {
            binding.tilCode.error = getString(R.string.error_register_otp)
            return false
        }
        return true
    }

    private fun clearErrorOnFocus() {

        binding.etPhone.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etPhone && hasFocus)
                binding.tilPhone.isErrorEnabled = !hasFocus
        }
        binding.etCode.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etCode && hasFocus)
                binding.tilCode.isErrorEnabled = !hasFocus
        }
    }


}