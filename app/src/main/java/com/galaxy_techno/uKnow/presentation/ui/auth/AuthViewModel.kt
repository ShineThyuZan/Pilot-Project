package com.galaxy_techno.uKnow.presentation.ui.auth

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galaxy_techno.uKnow.common.Constant
import com.galaxy_techno.uKnow.di.Qualifier
import com.galaxy_techno.uKnow.domain.AppRepository
import com.galaxy_techno.uKnow.domain.UserRepository
import com.galaxy_techno.uKnow.model.dto.*
import com.galaxy_techno.uKnow.presentation.extension.RemoteEvent
import com.galaxy_techno.uKnow.util.BitmapUtil
import com.galaxy_techno.uKnow.util.TypeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val appRepository: AppRepository,
    @Qualifier.Io private val io: CoroutineDispatcher
) : ViewModel() {

    // StateFlow == LiveData ( difference is StateFlow always has to declare initial value,
    // liveData is only on Main Thread and stateFlow can be on Any Coroutines Context ).
    private val _timer = MutableStateFlow(0)
    val timer: StateFlow<Int> get() = _timer

    fun setTimer(minute: Int) {
        val counter = minute * 60
        if (_timer.value == 0) {
            viewModelScope.launch {
                //count is from SERVER
                repeat(counter) {
                    _timer.value = (counter - it)
                    delay(1000L)
                }
                _timer.value = 0
            }
        }
    }

    //we don't want to initialize the initial value: so we used LiveData
    private val _sellerType = MutableLiveData<Int>()
    val sellerType: LiveData<Int> get() = _sellerType

    fun setSellerType(type: Int) {
        viewModelScope.launch {
            _sellerType.value = type
        }
    }

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> get() = _phoneNumber

    fun setPhoneNumber(phone : String) {
        viewModelScope.launch {
            _phoneNumber.value = phone
        }
    }

    private val _isOTPLoading = MutableStateFlow(false)
    val isOTPLoading: StateFlow<Boolean> get() = _isOTPLoading

    fun setOTPLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isOTPLoading.value = state
        }
    }

    private val _isVerifyingLoading = MutableStateFlow(false)
    val isVerifyingLoading: StateFlow<Boolean> get() = _isVerifyingLoading

    fun setVerifyingLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isVerifyingLoading.value = state
        }
    }

    private val _isLoginLoading = MutableStateFlow(false)
    val isLoginLoading: StateFlow<Boolean> get() = _isLoginLoading

    fun setLoginLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isLoginLoading.value = state
        }
    }

    private val _isRegisterLoading = MutableStateFlow(false)
    val isRegisterLoading: StateFlow<Boolean> get() = _isRegisterLoading

    fun setRegisterLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isRegisterLoading.value = state
        }
    }

    private val _isPwdForgetLoading = MutableStateFlow(false)
    val isPwdForgetLoading: StateFlow<Boolean> get() = _isPwdForgetLoading

    fun setPwdForgetLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isPwdForgetLoading.value = state
        }
    }

    private val _isPwdAddNewLoading = MutableStateFlow(false)
    val isPwdAddNewLoading: StateFlow<Boolean> get() = _isPwdAddNewLoading

    fun setPwdAddNewLoadingState(state: Boolean) {
        viewModelScope.launch {
            _isPwdForgetLoading.value = state
        }
    }

    //Login
    private val _loginChannel = Channel<RemoteEvent<LoginDTO>>()
    val loginEvent get() = _loginChannel.receiveAsFlow()

    fun triggerLogin(
        phone: String,
        password: String
    ) {
        val mobileNumber = "959$phone"
        viewModelScope.launch {

            _loginChannel.send(RemoteEvent.LoadingEvent())
            //todo : this will delete in deploy
            delay(1000L)

            userRepository.login(
                mobileNumber,
                password
            ).collect {

                it.data?.let { loginDTO ->

                    if (loginDTO.status == Constant.STATUS_SUCCESS) {
                        setLoginLoadingState(false)
                        setRegisterLoadingState(false)
                        _loginChannel.send(RemoteEvent.SuccessEvent(loginDTO))
                        saveAuthState()
                        saveAccessToken(loginDTO.data?.accessToken!!)
                        saveRefreshToken(loginDTO.data.refreshToken!!)
                        saveUserEntity(loginDTO.data)
                    } else {
                        setLoginLoadingState(false)
                        setRegisterLoadingState(false)
                        _loginChannel.send(
                            RemoteEvent.FailEvent(
                                loginDTO, loginDTO.error ?: "Fail request"
                            )
                        )
                    }
                } ?: run {
                    setLoginLoadingState(false)
                    setRegisterLoadingState(false)
                    _loginChannel.send(RemoteEvent.ErrorEvent(it.message ?: "Fail request"))
                }
            }
        }
    }

    //OTP request
    private val _otpChannel = Channel<RemoteEvent<OTPRequestDTO>>()
    val otpEvent get() = _otpChannel.receiveAsFlow()

    fun triggerOTP(
        mobile: String
    ) {

        val mobileNumber = "959$mobile"

        viewModelScope.launch {

            _otpChannel.send(RemoteEvent.LoadingEvent())

            userRepository.requestOTP(
                mobileNumber
            ).collect {

                delay(2000L)

                it.data?.let { otp ->
                    if (otp.status == Constant.STATUS_SUCCESS) {
                        setOTPLoadingState(false)
                        _otpChannel.send(RemoteEvent.SuccessEvent(otp))
                    } else {
                        setOTPLoadingState(false)
                        _otpChannel.send(RemoteEvent.FailEvent(otp, otp.error ?: "Fail request"))
                    }
                } ?: run {
                    setOTPLoadingState(false)
                    _otpChannel.send(RemoteEvent.ErrorEvent(it.message ?: "Error"))
                }
            }
        }
    }

    //OTP validation
    private val _validateChannel = Channel<RemoteEvent<OTPValidateDTO>>()
    val validateEvent get() = _validateChannel.receiveAsFlow()

    fun triggerValidate(
        mobile: String,
        otpCode: String
    ) {

        val mobileNumber = "959$mobile"

        viewModelScope.launch {

            _validateChannel.send(RemoteEvent.LoadingEvent())

            userRepository.validateOTP(
                mobileNumber, otpCode
            ).collect {

                delay(2000L)

                it.data?.let { otp ->
                    if (otp.status == Constant.STATUS_SUCCESS) {
                        setVerifyingLoadingState(false)
                        _validateChannel.send(RemoteEvent.SuccessEvent(otp))
                    } else {
                        setVerifyingLoadingState(false)
                        _validateChannel.send(
                            RemoteEvent.FailEvent(
                                otp,
                                otp.error ?: "Fail request"
                            )
                        )
                    }
                } ?: run {
                    setVerifyingLoadingState(false)
                    _validateChannel.send(RemoteEvent.ErrorEvent(it.message ?: "Error"))
                }
            }
        }
    }


    //register
    private val _registerChannel = Channel<RemoteEvent<RegisterDTO>>()
    val registerEvent get() = _registerChannel.receiveAsFlow()

    fun triggerRegister(
        name: String,
        email: String,
        mobile: String,
        password: String
    ) {
        val mobileNumber = "959$mobile"
        val sellerEmail = "$email@gmail.com"

        viewModelScope.launch {

            _registerChannel.send(RemoteEvent.LoadingEvent())

            userRepository.register(
                sellerType.value ?: 1,
                1,
                name,
                sellerEmail,
                mobileNumber,
                password

            ).collect {

                delay(2000L)

                it.data?.let { register ->
                    if (register.status == Constant.STATUS_SUCCESS) {
                        _registerChannel.send(RemoteEvent.SuccessEvent(register))
                        triggerLogin(mobile, password)
                    } else {
                        setRegisterLoadingState(false)
                        _registerChannel.send(
                            RemoteEvent.FailEvent(
                                register, register.error ?: "Fail request"
                            )
                        )
                    }
                } ?: run {
                    setRegisterLoadingState(false)
                    _registerChannel.send(RemoteEvent.ErrorEvent(it.message ?: "Error"))
                }
            }
        }
    }

    //pwd forget
    private val _pwdForgetChannel = Channel<RemoteEvent<DefaultResponse>>()
    val pwdForgetEvent get() = _pwdForgetChannel.receiveAsFlow()

    fun triggerPwdForget() {
        viewModelScope.launch {
            //do request to userRepo
        }
    }

    //pwd add new
    private val _pwdAddNewChannel =Channel<RemoteEvent<DefaultResponse>>()
    val pwdAddNewEvent get() = _pwdAddNewChannel.receiveAsFlow()

    fun triggerPwdAddNew() {
        viewModelScope.launch {
            //do request to userRepo
        }
    }

    private fun saveAuthState() {
        viewModelScope.launch(io) {
            appRepository.storeAuthState(true)
        }
    }

    private fun saveAccessToken(token: String) {
        viewModelScope.launch(io) {
            appRepository.saveAccessToken(token)
        }
    }

    private fun saveRefreshToken(token: String) {
        viewModelScope.launch(io) {
            appRepository.saveRefreshToken(token)
        }
    }

    private fun saveUserEntity(user: UserData) {
        viewModelScope.launch(io) {
            val entity = user.toEntity()
            userRepository.saveUser(entity)
        }
    }

    //upload avatar
    fun uploadAvatar(
        id: Int,
        type: String,
        image: Bitmap
    ) {
        viewModelScope.launch {
            userRepository.uploadAvatar(
                id,
                type,
                TypeConverter.createPartFile(
                    "image",
                    BitmapUtil.getBytes(image,100)
                )
            ).collect {
                //lol
            }
        }
    }

    private val _responseCountries = MutableLiveData<RemoteEvent<CountryListDTO>>()
    val responseCountries : LiveData<RemoteEvent<CountryListDTO>> get() = _responseCountries
    fun getCountries() {
        viewModelScope.launch {
            appRepository.getCountries().collect {
                _responseCountries.postValue(it)
            }
        }
    }

}