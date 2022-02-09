package com.galaxy_techno.uKnow.common

object Constant {
    const val DB_NAME = "chat_db"
    const val DS_NAME = "chat_ds"
    const val USER_TABLE = "chat_user_table"

    const val BUSINESS_TYPE_ONE = 1
    const val BUSINESS_TYPE_TWO = 2
    const val BUSINESS_TYPE_THREE = 3

    const val BUSINESS_TYPE_ONE_NAME = "Individual"
    const val BUSINESS_TYPE_TWO_NAME =  "Business"
    const val BUSINESS_TYPE_THREE_NAME =  "Branded"

    const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.{1,})"

    const val EMAIL_REGEX_ADDRESS = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

    const val STATUS_FAIL = "FAIL"
    const val STATUS_SUCCESS = "SUCCESS"

    const val AVATAR_WIDTH_SMALL = 128
    const val AVATAR_WIDTH_MEDIUM = 256
    const val AVATAR_WIDTH_LARGE = 512
    const val AVATAR_BACKGROUND_WIDTH = 640
    const val AVATAR_BACKGROUND_HEIGHT = 360
}