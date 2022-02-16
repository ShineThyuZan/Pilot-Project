package com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.discover

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.TopLvlFeedsBinding

import com.galaxy_techno.uKnow.presentation.base.TopFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopFeedsFragment : TopFragment<TopLvlFeedsBinding>(TopLvlFeedsBinding::inflate) {

    override fun setupView() {
        super.setupView()
       binding.toolbar.topLvlToolbar.title = "Discover"
    }
    override fun setupListener() {
        super.setupListener()
//        binding.toolbar.menuProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_employee_to_profile)
//        }
    }
}