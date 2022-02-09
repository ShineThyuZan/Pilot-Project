package com.galaxy_techno.uKnow.presentation.ui.auth.otp

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.SheetPhoneNoExistBinding
import com.galaxy_techno.uKnow.presentation.base.BaseBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhoneNoAdyExistedBottomSheet :
    BaseBottomSheet<SheetPhoneNoExistBinding>(SheetPhoneNoExistBinding::inflate) {

    private val args: PhoneNoAdyExistedBottomSheetArgs by navArgs()

    override fun initialize() {
        super.initialize()
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun setupView() {
        super.setupView()
        binding.tvTitle.text = requireContext().getString(R.string.title_phone_existed, args.phone)
    }

    override fun setupListener() {
        super.setupListener()
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {

        val directions = PhoneNoAdyExistedBottomSheetDirections.actionSheetToLogin(args.phone)
        findNavController().navigate(directions)
    }


}