package com.galaxy_techno.uKnow.presentation.ui.auth.business_info

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.FragmentBusinessInfoBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BusinessInfoFragment :
    AuthFragment<FragmentBusinessInfoBinding>(FragmentBusinessInfoBinding::inflate) {

    override fun setupListener() {
        super.setupListener()
        binding.btnNext.setOnClickListener {
            setScrollPager()
        }
        binding.btnSkip.setOnClickListener {
            goToTypeChoose()
        }
    }

    private fun goToTypeChoose() {
        findNavController().navigate(
            R.id.action_info_to_type
        )
    }

    private fun setScrollPager() {
        when (binding.infoPager.currentItem) {
            0 -> {
                binding.infoPager.currentItem = 1
            }
            1 -> {
                binding.infoPager.currentItem = 2
            }
            2 -> {
                binding.infoPager.currentItem = 3

            }
            3 -> {
                goToTypeChoose()
            }
        }
    }

    override fun setupView() {
        super.setupView()
        setupPager()
    }

    private var callback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 3) {
                setVisibleNextButton(true)
            } else setVisibleNextButton(false)
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

    private fun setupPager() {
        val groupAdapter = BusinessInfoAdapter(requireActivity().supportFragmentManager, lifecycle)
        binding.infoPager.apply {
            this.isUserInputEnabled = true
            this.adapter = groupAdapter
            this.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            this.registerOnPageChangeCallback(callback)

            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }


        TabLayoutMediator(binding.tab, binding.infoPager) { tab, position ->

            when (position) {
                0 -> {
                    //do something
                }
                1 -> {
                    //do something
                }
                2 -> {
                    //do something
                }
                3 -> {
                    //do something
                }
            }

        }.attach()
    }

    private fun setVisibleNextButton(isLastIndex: Boolean) {
        if (isLastIndex) {
            binding.btnSkip.visibility = View.GONE
        } else binding.btnSkip.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        binding.infoPager.unregisterOnPageChangeCallback(callback)
        super.onDestroyView()
    }
}