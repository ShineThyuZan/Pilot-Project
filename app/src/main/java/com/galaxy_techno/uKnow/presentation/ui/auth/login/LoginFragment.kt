package com.galaxy_techno.uKnow.presentation.ui.auth.login

import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.common.InternetChecker
import com.galaxy_techno.uKnow.common.NetworkStatus
import com.galaxy_techno.uKnow.databinding.FragmentLoginBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.galaxy_techno.uKnow.presentation.ui.auth.AuthViewModel
import com.galaxy_techno.uKnow.presentation.extension.*
import com.galaxy_techno.uKnow.presentation.single_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : AuthFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    @Inject
    lateinit var internetChecker: InternetChecker
    private var internetStatus: Boolean = false

    private val viewModel: AuthViewModel by activityViewModels()

    private val animText by lazy {
        binding.imgLogo.drawable as Animatable
    }
    private val args: LoginFragmentArgs by navArgs()

    override fun initialize() {
        super.initialize()
        (activity as MainActivity).shouldShowToolbar(false)
    }

    override fun setupView() {
        super.setupView()

        setExistedPhone()
        clearErrorOnFocus()
        recursiveAnimation()

        binding.etPhone.addTextChangedListener {
            binding.tilPhone.setCloseable(it)
        }
    }
    private fun setExistedPhone(){
        args.phoneExisted?.let {
            binding.etPhone.setText(it)
        }
    }

    private fun recursiveAnimation() {
        animText.stop()
        animText.start()
    }

    override fun setupListener() {
        super.setupListener()

        binding.imgLogo.setOnClickListener {
            recursiveAnimation()
        }

        binding.btnLogin.setOnClickListener {

            if (validate()) {
                if (!internetStatus) {
                    binding.root.displaySnack(getString(R.string.snack_no_internet))
                } else {
                    viewModel.setLoginLoadingState(true)

                    viewModel.triggerLogin(
                        binding.etPhone.text?.trim().toString(),
                        binding.etPwd.text?.trim().toString()
                    )

                }
            }
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_sheet_to_login)
        }
        binding.btnForgetPwd.setOnClickListener {

            val directions =
                LoginFragmentDirections.actionLoginToPwdForget(
                    binding.etPhone.text.toString()
                )
            findNavController().navigate(directions)
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
            }
        }

        // Collects from the flow when the View is at least STARTED and
        // SUSPENDS the collection when the lifecycle is STOPPED.
        // Collecting the flow cancels when the View is DESTROYED.

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            //use StateFlow with collectLatest EVER
            viewModel.isLoginLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_login))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.loginEvent.collectLatest {
                when (it) {
                    is RemoteEvent.LoadingEvent -> {
                        viewModel.setLoginLoadingState(true)
                    }
                    is RemoteEvent.ErrorEvent -> {

                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_login_fail)
                        )
                    }
                    is RemoteEvent.FailEvent -> {

                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is RemoteEvent.SuccessEvent -> {

                        binding.btnLogin.setActive(false)
//                        binding.btnForgetPwd.setActive(false)
                        binding.btnRegister.setActive(false)
                        binding.root.displaySnack(getString(R.string.snack_request_login_success))
                        delay(500L)
                        navigateToHome()
                    }
                }
            }

        }

    }

    private fun navigateToHome() {
        val authNavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.dest_top_chat, true)
            .build()
        findNavController().navigate(R.id.action_login_to_home, Bundle(), authNavOptions)
    }

    private fun validate(): Boolean {
        if (!binding.etPhone.isVerifiedPhone()) {
            binding.tilPhone.error = getString(R.string.error_login_phone)
            return false
        }
        if (!binding.etPwd.isVerifiedPwd()) {
            binding.tilPwd.error = getString(R.string.error_login_pwd_8)
            return false
        }
        return true
    }

    private fun clearErrorOnFocus() {
        binding.etPhone.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etPhone && hasFocus)
                binding.tilPhone.isErrorEnabled = !hasFocus
        }
        binding.etPwd.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etPwd && hasFocus)
                binding.tilPwd.isErrorEnabled = !hasFocus

        }
    }
}