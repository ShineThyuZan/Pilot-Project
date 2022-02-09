package com.galaxy_techno.uKnow.presentation.ui.auth.business_info

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class BusinessInfoAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BusinessInfoWelcomeFragment()
            1 -> return BusinessInfoOneFragment()
            2 -> return BusinessInfoTwoFragment()
            3 -> return BusinessInfoThreeFragment()
        }
        return BusinessInfoWelcomeFragment()
    }
}