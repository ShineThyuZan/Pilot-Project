package com.galaxy_techno.uKnow.presentation.ui.auth.business_info

import android.graphics.drawable.Animatable
import com.galaxy_techno.uKnow.databinding.FragmentBusinessWelcomeBinding
import com.galaxy_techno.uKnow.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessInfoWelcomeFragment :
    BaseFragment<FragmentBusinessWelcomeBinding>(FragmentBusinessWelcomeBinding::inflate) {

    override fun setupView() {
        super.setupView()

        (binding.img.drawable as Animatable).start()

    }

    override fun setupListener() {
        super.setupListener()
        binding.img.setOnClickListener {
            recursiveAnimation()
        }
    }

    private fun recursiveAnimation() {
        val logo = binding.img.drawable as Animatable
        logo.stop()
        logo.start()
    }
}