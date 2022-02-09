package com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.orders

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.TopLvlContactBinding
import com.galaxy_techno.uKnow.presentation.base.TopFragment
import com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.orders.tabs.OrderPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TopContactFragment : TopFragment<TopLvlContactBinding>(TopLvlContactBinding::inflate) {

    override fun setupView() {
        super.setupView()
       binding.toolbar.topLvlToolbar.title = getString(R.string.btn_nav_contact)
        setupPager()
    }

    override fun setupListener() {
        super.setupListener()
        binding.toolbar.menuProfile.setOnClickListener {
            findNavController().navigate(R.id.action_order_to_profile)
        }
    }

    private fun setupPager() {

        val orderAdapter = OrderPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.orderPager.apply {
            this.isUserInputEnabled = true
            this.adapter = orderAdapter
            this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.registerOnPageChangeCallback(orderPagerCallback)

            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }


        TabLayoutMediator(binding.orderTab, binding.orderPager) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = getString(R.string.order_all)
                }
                1 -> {
                    tab.text = getString(R.string.order_pending)
                }
                2 -> {
                    tab.text = getString(R.string.order_processing)
                }
                3 -> {
                    tab.text = getString(R.string.order_delivered)
                }
                4 -> {
                    tab.text = getString(R.string.order_refunded)
                }
                5 -> {
                    tab.text = getString(R.string.order_completed)
                }

            }
            //todo something

        }.attach()
    }

    private var orderPagerCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            Timber.tag("group pager selected").d(position.toString())
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            Timber.tag("group scroll state").d(state.toString())
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            Timber.tag("group pager scrolled").d(position.toString())
        }
    }

    override fun onDestroyView() {
        binding.orderPager.unregisterOnPageChangeCallback(orderPagerCallback)
        super.onDestroyView()
    }

}