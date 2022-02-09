package com.galaxy_techno.uKnow.common

object Endpoint {

    //mock
    const val API_KEY = "cdbea55de27a909b4aaa2cfc02eabb75"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/original/"

    //API URLs
    const val API_HOST = "http://192.168.100.241:8080/"

    private const val SELLER = "chat/"
    private const val TOKEN = "token/"
    private const val MASTER_DATA = "masterData/"

    private const val REGISTER_PATH = "register/"
    private const val LOGIN_PATH = "login/"

    const val AUTHORIZATION = "Authorization"

    //MASTER DATA
    const val MASTER_CATEGORY_LIST = API_HOST + SELLER + MASTER_DATA + "getCategoryList"
    const val MASTER_COUNTRY_LIST = API_HOST + SELLER + MASTER_DATA + "getCountryList"
    const val MASTER_STATE_LIST = API_HOST + SELLER + MASTER_DATA + "getStateList"
    const val MASTER_TOWNSHIP_LIST = API_HOST + SELLER + MASTER_DATA + "getTownshipList"
    const val MASTER_STATE_WITH_TOWNSHIP_LIST = API_HOST + SELLER + MASTER_DATA + "all"
    const val MASTER_PAYMENT_TYPE_LIST = API_HOST + SELLER + MASTER_DATA + "getAccountTypeList"
    const val MASTER_SELLER_MENU_LIST = API_HOST + SELLER + MASTER_DATA + "getSellerMenuList"

    //OTP
    const val GET_OTP = API_HOST + SELLER + REGISTER_PATH + "getOTP"
    const val VALIDATE_OTP = API_HOST + SELLER + REGISTER_PATH + "validateOTP"

    //AUTH
//    chat/token/refresh
    const val REFRESH_TOKEN = API_HOST + SELLER + TOKEN + "refresh"
    const val REGISTER = API_HOST + SELLER + REGISTER_PATH + "chatRegister"
    const val EMPLOYEE_REGISTER = API_HOST + SELLER + "doLogin"
    const val LOGIN = API_HOST + SELLER +  "doLogin"
    const val FORGET_PASSWORD = API_HOST + "forget_password"


    //PROFILE
    const val SAVE_ADDRESS_INFO = API_HOST + SELLER + REGISTER_PATH + "saveDraftSellerAddress"
    const val SAVE_PAYMENT_INFO = API_HOST + SELLER + REGISTER_PATH + "saveDraftSellerPaymentInfo"
    const val SAVE_BUSINESS_INFO =
        API_HOST + SELLER + REGISTER_PATH + "saveDraftSellerBusinessIdentity"
    const val REGISTER_ADDRESS_INFO = API_HOST + SELLER + REGISTER_PATH + "registerSellerAddress"
    const val REGISTER_PAYMENT_INFO =
        API_HOST + SELLER + REGISTER_PATH + "registerSellerPaymentInfo"
    const val REGISTER_BUSINESS_INFO =
        API_HOST + SELLER + REGISTER_PATH + "registerSellerBusinessIdentity"
    const val RELOAD_ADDRESS_INFO = API_HOST + SELLER + REGISTER_PATH + "reloadSaveSellerAddress"
    const val RELOAD_PAYMENT_INFO =
        API_HOST + SELLER + REGISTER_PATH + "reloadSaveSellerPaymentInfo"
    const val RELOAD_BUSINESS_INFO =
        API_HOST + SELLER + REGISTER_PATH + "reloadSaveSellerBusinessIdentity"

}