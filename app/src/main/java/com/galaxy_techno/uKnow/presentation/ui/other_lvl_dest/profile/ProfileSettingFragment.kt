package com.galaxy_techno.uKnow.presentation.ui.other_lvl_dest.profile

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.databinding.FragmentProfileSettingBinding
import com.galaxy_techno.uKnow.model.entity.User
import com.galaxy_techno.uKnow.presentation.base.OtherLvlFragment
import com.galaxy_techno.uKnow.util.NotificationUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileSettingFragment :
    OtherLvlFragment<FragmentProfileSettingBinding>(FragmentProfileSettingBinding::inflate) {
    private val viewModel: ProfileSettingViewModel by viewModels()

    @Inject
    lateinit var notifier: NotificationUtil

    override fun setupView() {
        super.setupView()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.seller_yellow_600)
    }

    override fun setupListener() {
        super.setupListener()
        binding.toolbar.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvAccount.setOnClickListener { }
        binding.tvGeneral.setOnClickListener { }
        binding.tvStatement.setOnClickListener { }
        binding.tvNotification.setOnClickListener {
            makeNotify()
        }
        binding.tvChat.setOnClickListener { }
        binding.tvLanguage.setOnClickListener { }
        binding.tvTutorial.setOnClickListener { }
        binding.tvHelpCenter.setOnClickListener { }
        binding.tvFeedback.setOnClickListener { }
        binding.tvAbout.setOnClickListener { }
        binding.tvLogout.setOnClickListener { }
    }

    override fun observe() {
        super.observe()

        viewModel.user.observe(viewLifecycleOwner) {
            //setupUser(it)
        }
    }

    private fun makeNotify() {
        //if you want to make argument
        // .setArgument(Bundle()) like argument in navigation

        val pendingIntent = findNavController()
            .createDeepLink()
            .setDestination(R.id.dest_top_chat)
            .createPendingIntent()

        notifier.clearAllNotification()
        notifier.showNotification(
            "Go to Dashboard", pendingIntent
        )

    }

    private fun setupUser(user: User) {
        user.apply {
            binding.tvName.text = shopName
            binding.tvShop.text = sellerId.toString()
        }
    }
}