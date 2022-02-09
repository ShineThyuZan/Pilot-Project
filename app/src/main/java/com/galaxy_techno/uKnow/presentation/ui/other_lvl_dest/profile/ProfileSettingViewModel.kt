package com.galaxy_techno.uKnow.presentation.ui.other_lvl_dest.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.uKnow.domain.UserRepository
import com.galaxy_techno.uKnow.model.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSettingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user


    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            userRepository.getUser().collect {
                _user.value = it
            }
        }
    }
}