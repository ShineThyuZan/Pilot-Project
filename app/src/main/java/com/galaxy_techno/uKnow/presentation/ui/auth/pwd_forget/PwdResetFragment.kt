package com.galaxy_techno.uKnow.presentation.ui.auth.pwd_forget

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.FragmentPasswordResetBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.galaxy_techno.uKnow.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PwdResetFragment :
    AuthFragment<FragmentPasswordResetBinding>(FragmentPasswordResetBinding::inflate) {
    private val viewModel: AuthViewModel by activityViewModels()
    override fun setupListener() {
        super.setupListener()
        binding.btnChange.setOnClickListener {
            findNavController().navigate(R.id.action_pwd_reset_to_successful)
        }
    }
}