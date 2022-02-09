package com.galaxy_techno.uKnow.presentation.ui.auth.pwd_forget

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.FragmentPasswordResetSuccessfulBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.galaxy_techno.uKnow.presentation.ui.auth.AuthViewModel
import com.galaxy_techno.uKnow.presentation.single_activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PwdResetSuccessfulFragment :
    AuthFragment<FragmentPasswordResetSuccessfulBinding>(FragmentPasswordResetSuccessfulBinding::inflate) {
    private val viewModel: AuthViewModel by activityViewModels()

    override fun initialize() {
        super.initialize()
        (activity as MainActivity).shouldShowToolbar(false)
    }

    override fun setupListener() {
        super.setupListener()
        binding.btnLogin.setOnClickListener {
            val authNavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.dest_login, true)
                .build()
            findNavController().navigate(R.id.action_successful_to_login, Bundle(), authNavOptions)
        }
    }
}