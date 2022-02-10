package com.galaxy_techno.uKnow.presentation.ui.top_lvl_dest.profile

import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.galaxy_techno.uKnow.databinding.TopLvlProfileBinding
import com.galaxy_techno.uKnow.presentation.base.TopFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopProfileFragment : TopFragment<TopLvlProfileBinding>(TopLvlProfileBinding::inflate) {

    override fun setupListener() {
        super.setupListener()
        onHandleClicks()
    }

    private fun onHandleClicks(){
        binding.profileInfoClick.setOnClickListener {
          //  findNavController().navigate(R.id.action_to_profile_information)
        }
        binding.itemWallet.setOnClickListener {
            setToast(it) }
        binding.itemReceive.setOnClickListener {
            setToast(it) }
        binding.itemScan.setOnClickListener {
            setToast(it) }
        binding.itemQr.setOnClickListener {
            setToast(it) }
        binding.itemPackage.setOnClickListener {
            setToast(it)}
        binding.itemVip.setOnClickListener {
            setToast(it) }
        binding.itemVerified.setOnClickListener {
            setToast(it)  }
        binding.itemSecurity.setOnClickListener {
            setToast(it) }
        binding.itemAlbum.setOnClickListener {
            setToast(it) }
        binding.itemCollection.setOnClickListener {
            setToast(it) }
        binding.itemEmoji.setOnClickListener {
            setToast(it) }
        binding.itemSkin.setOnClickListener {
            setToast(it) }
        binding.itemDocument.setOnClickListener {
            setToast(it) }
        binding.itemLive.setOnClickListener {
            setToast(it) }
        binding.itemSetting.setOnClickListener {
         //   findNavController().navigate(R.id.action_to_profile_setting)
        }
    }

    private fun setToast(view : View){
        val t = (view as TextView).text.toString()
        showToast(t)
    }

    private fun showToast(msg : String){
        Toast.makeText(requireContext(),msg+"  မကြာမှီ လာမည်", Toast.LENGTH_SHORT).show()
    }
}