package com.galaxy_techno.uKnow.presentation.ui.auth.pwd_forget

import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.FragmentPasswordForgetBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.galaxy_techno.uKnow.presentation.base.OtherLvlFragment
import com.galaxy_techno.uKnow.presentation.extension.isVerifiedName
import com.galaxy_techno.uKnow.presentation.extension.isVerifiedPhone
import com.galaxy_techno.uKnow.presentation.extension.setCloseable
import com.galaxy_techno.uKnow.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PwdForgetFragment : OtherLvlFragment<FragmentPasswordForgetBinding>(
    FragmentPasswordForgetBinding::inflate
) {
    private val viewModel : AuthViewModel by activityViewModels()

    override fun setupView() {
        super.setupView()
        binding.toolbar.tvToolbarTitle.text = "Forget Password"
        clearErrorOnFocus()
    }

    override fun setupListener() {
        super.setupListener()
//        binding.btnCode.setOnClickListener {  }
//        binding.btnVerify.setOnClickListener {
//            if (validate()) findNavController().navigate(R.id.action_pwd_forget_to_reset)
//        }
//
//        binding.etPhone.addTextChangedListener {
//            binding.tilPhone.setCloseable(it)
//            it?.let { text ->
//                binding.btnCode.isVisible = text.isVerifiedPhone()
//            }
//        }
    }

    private fun validate() : Boolean {
//        if (!binding.etPhone.isVerifiedPhone()) {
//            binding.tilPhone.error = getString(R.string.error_register_phone)
//            return false
//        }
//        if (!binding.etCode.isVerifiedName()) {
//            binding.tilCode.error = getString(R.string.error_register_otp)
//            return false
//        }
        return true
    }

    private fun clearErrorOnFocus() {

//        binding.etPhone.setOnFocusChangeListener { v, hasFocus ->
//            if (v == binding.etPhone && hasFocus)
//                binding.tilPhone.isErrorEnabled = !hasFocus
//        }
//        binding.etCode.setOnFocusChangeListener { v, hasFocus ->
//            if (v == binding.etCode && hasFocus)
//                binding.tilCode.isErrorEnabled = !hasFocus
//        }
    }


}