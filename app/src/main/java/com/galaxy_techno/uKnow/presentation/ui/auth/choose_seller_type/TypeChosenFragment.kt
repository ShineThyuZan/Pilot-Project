package com.galaxy_techno.uKnow.presentation.ui.auth.choose_seller_type

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.galaxy_techno.uKnow.R
import com.galaxy_techno.uKnow.common.Constant
import com.galaxy_techno.uKnow.databinding.FragmentTypeChooseBinding
import com.galaxy_techno.uKnow.presentation.base.AuthFragment
import com.galaxy_techno.uKnow.presentation.extension.displaySnack
import com.galaxy_techno.uKnow.presentation.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeChosenFragment : AuthFragment<FragmentTypeChooseBinding>(
    FragmentTypeChooseBinding::inflate
) {

    private val viewModel: AuthViewModel by activityViewModels()

    override fun setupListener() {
        super.setupListener()
        binding.btnContinue.setOnClickListener {
            if (
                binding.radioGroupType.checkedRadioButtonId == R.id.type_one
                || binding.radioGroupType.checkedRadioButtonId == R.id.type_two
                || binding.radioGroupType.checkedRadioButtonId == R.id.type_three
            ) {
                findNavController().navigate(R.id.action_type_to_otp)
            } else binding.root.displaySnack(getString(R.string.snack_type_choose))
        }

        binding.radioGroupType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.type_one -> {
                    saveToVM(Constant.BUSINESS_TYPE_ONE)
                }
                R.id.type_two -> {
                    saveToVM(Constant.BUSINESS_TYPE_TWO)
                }
                R.id.type_three -> {
                    saveToVM(Constant.BUSINESS_TYPE_THREE)
                }
            }
        }
    }

    private fun saveToVM(type: Int) {
        viewModel.setSellerType(type)

    }
}