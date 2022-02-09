package com.galaxy_techno.uKnow.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object TypeConverter {

    fun createPartFromString(
        str: String?
    ) = (str ?: "").toRequestBody(MultipartBody.FORM)

    fun createPartFile(
        context: Context,
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part {
        val file = FileUtils.getFile(context, fileUri)
        val requestFile =
            file!!.asRequestBody(context.contentResolver.getType(fileUri)!!.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun createPartFile(
        context: Context,
        partName: String,
        fileUri: Uri,
        file: File
    ): MultipartBody.Part {
        val requestFile =
            file.asRequestBody(context.contentResolver.getType(fileUri)!!.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    fun createPartFile(
        partName: String,
        bytes: ByteArray
    ): MultipartBody.Part {
        val rb = bytes.toRequestBody("image/jpeg".toMediaType())
        return MultipartBody.Part.createFormData(partName, "image.jpg", rb)
    }

}