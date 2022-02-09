package com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.products

import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.TopLvlProductsBinding
import com.galaxy_techno.uKnow.presentation.base.TopFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopProductsFragment : TopFragment<TopLvlProductsBinding>(TopLvlProductsBinding::inflate) {

    override fun setupView() {
        super.setupView()
        binding.toolbar.topLvlToolbar.title = getString(R.string.btn_nav_group)
    }
    override fun setupListener() {
        super.setupListener()
        binding.toolbar.menuProfile.setOnClickListener {
    //        findNavController().navigate(R.id.action_product_to_profile)
        }
    }
}