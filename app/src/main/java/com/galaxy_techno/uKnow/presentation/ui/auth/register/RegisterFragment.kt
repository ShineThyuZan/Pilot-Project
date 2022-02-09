package com.galaxy_techno.uKnow.presentation.ui.auth.register

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galaxy_techno.uKnow.BuildConfig
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.common.Constant
import com.galaxy_techno.uKnow.common.InternetChecker
import com.galaxy_techno.uKnow.common.NetworkStatus
import com.galaxy_techno.uKnow.databinding.FragmentRegisterBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.galaxy_techno.uKnow.presentation.extension.*
import com.galaxy_techno.uKnow.presentation.single_activity.MainActivity
import com.galaxy_techno.uKnow.presentation.ui.auth.AuthViewModel
import com.galaxy_techno.uKnow.util.BitmapUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : AuthFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {
    private val viewModel: AuthViewModel by activityViewModels()
    private val args: RegisterFragmentArgs by navArgs()

    @Inject
    lateinit var internetChecker: InternetChecker
    private var internetStatus: Boolean = false

    private var tempUri: Uri? = null
    private val fromCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            tempUri?.let { uri ->
                binding.imgUpload.setImageURI(uri)
                //todo change uriToBitmap
                val bitmap = BitmapUtil.getBitmap(uri, requireActivity())
            }
        }
    }
    private val fromGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        it?.let { uri ->
            binding.imgUpload.setImageURI(uri)
            val bitmap = BitmapUtil.getBitmap(uri, requireActivity())
        }
    }

    private fun takeImageFromCamera() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            getTmpFileUri().let {
                tempUri = it
                fromCamera.launch(it)
            }
        }
    }

    private fun takeImageFromGallery() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            fromGallery.launch("image/*")
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", requireActivity().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    override fun setupView() {
        super.setupView()
        clearErrorOnFocus()
        setupPhoneNumber(args.phone)
    }

    override fun setupListener() {
        super.setupListener()

        binding.imgUpload.setOnClickListener {
            //todo : do choose from Camera or Gallery Dialog
            takeImageFromGallery()
        }

        binding.btnRegister.setOnClickListener {
            if (validate()) {

                if (!internetStatus) {
                    binding.root.displaySnack(getString(R.string.snack_no_internet))
                } else {
                    viewModel.triggerRegister(
                        binding.etName.text?.trim().toString(),
                        binding.etEmail.text?.trim().toString(),
                        binding.etPhone.text?.trim().toString(),
                        binding.etPassword.text?.trim().toString()
                    )
                    viewModel.setRegisterLoadingState(true)
                }
            }
        }

        binding.btnTerms.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_terms)
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

        viewModel.sellerType.observe(viewLifecycleOwner) {
            when (it) {
                Constant.BUSINESS_TYPE_ONE -> {
                    binding.tvType.apply {
                        text = Constant.BUSINESS_TYPE_ONE_NAME
                        this.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(
                                requireContext(), R.drawable.ic_type_one_individual_icon
                            ), null, null, null
                        )
                    }

                }
                Constant.BUSINESS_TYPE_TWO -> {
                    binding.tvType.apply {
                        text = Constant.BUSINESS_TYPE_TWO_NAME
                        this.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(
                                requireContext(), R.drawable.ic_type_two_business_icon
                            ), null, null, null
                        )

                    }
                }
                Constant.BUSINESS_TYPE_THREE -> {
                    binding.tvType.apply {
                        text = Constant.BUSINESS_TYPE_THREE_NAME
                        this.setCompoundDrawablesWithIntrinsicBounds(
                            ContextCompat.getDrawable(
                                requireContext(), R.drawable.ic_type_three_branded_icon
                            ), null, null, null
                        )

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.registerEvent.collectLatest {
                when (it) {

                    is RemoteEvent.LoadingEvent -> {
                        viewModel.setRegisterLoadingState(true)
                    }
                    is RemoteEvent.ErrorEvent -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_register_fail)
                        )
                    }
                    is RemoteEvent.FailEvent -> {
                        binding.root.displaySnack(
                            it.data?.error ?: getString(R.string.snack_request_register_fail)
                        )
                    }
                    is RemoteEvent.SuccessEvent -> {
                        //just show loading, now fetch the triggerLogin in VM
                        viewModel.setRegisterLoadingState(true)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.loginEvent.collectLatest {
                when (it) {

                    is RemoteEvent.LoadingEvent -> {
                        viewModel.setRegisterLoadingState(true)
                    }
                    is RemoteEvent.ErrorEvent -> {
                        binding.root.displaySnack(
                            it.message ?: getString(R.string.snack_request_register_fail)
                        )
                    }
                    is RemoteEvent.FailEvent -> {
                        binding.root.displaySnack(it.data?.error!!)
                    }
                    is RemoteEvent.SuccessEvent -> {
                        binding.root.displaySnack(getString(R.string.snack_request_register_success))
                        binding.btnRegister.setActive(false)
                        delay(500L)
                        navigateToHome()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.isRegisterLoading.collectLatest {
                if (it) {
                    (activity as MainActivity).showLoadingDialog(getString(R.string.dialog_register))
                } else {
                    (activity as MainActivity).hideLoadingDialog()
                }
            }
        }
    }

    private fun navigateToHome() {

        val authNavOptions = NavOptions.Builder()
            .setPopUpTo(R.id.dest_top_chat, true)
            .build()
        findNavController().navigate(R.id.action_register_to_home, Bundle(), authNavOptions)
    }

    private fun validate(): Boolean {

        if (!binding.etName.isVerifiedName()) {
            binding.tilName.error = getString(R.string.error_register_name)
            return false
        }

        if (!binding.etEmail.isVerifiedEmail()) {
            binding.tilEmail.error = getString(R.string.error_register_email)
            return false
        }

        if (!binding.etPhone.isVerifiedPhone()) {
            binding.tilPhone.error = getString(R.string.error_register_phone)
            return false
        }
        if (!binding.etPassword.isVerifiedPwd()) {
            binding.tilPassword.error = getString(R.string.error_register_pwd)
            return false
        }
        if (!binding.etConfirmPassword.isVerifiedPwd()) {
            binding.tilConfirmPassword.error = getString(R.string.error_register_pwd)
            return false
        }
        if (!binding.etConfirmPassword.isIdentifiedPwd(binding.etPassword.text)) {
            binding.tilConfirmPassword.error = getString(R.string.error_register_pwd_confirm)
            return false
        }
        if (!binding.checkboxTerms.isChecked) {
            binding.root.displaySnack(getString(R.string.snack_terms))
            return false
        }

        return true
    }

    private fun clearErrorOnFocus() {

        binding.etName.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etName && hasFocus)
                binding.tilName.isErrorEnabled = !hasFocus
        }
        binding.etEmail.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etEmail && hasFocus)
                binding.tilEmail.isErrorEnabled = !hasFocus
        }

        binding.etPassword.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etPassword && hasFocus)
                binding.tilPassword.isErrorEnabled = !hasFocus
        }
        binding.etConfirmPassword.setOnFocusChangeListener { v, hasFocus ->
            if (v == binding.etConfirmPassword && hasFocus)
                binding.tilConfirmPassword.isErrorEnabled = !hasFocus
        }
    }

    private fun setupPhoneNumber(phone: String) {
        binding.etPhone.setText(phone)
        binding.etPhone.setActive(false)
    }

}