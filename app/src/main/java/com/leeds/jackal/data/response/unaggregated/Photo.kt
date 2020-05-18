package com.leeds.jackal.data.response.unaggregated


import com.google.gson.annotations.SerializedName

data class Photo(
    val highres: String,
    @SerializedName("is_user_uploaded")
    val isUserUploaded: Boolean,
    val thumb: String
)