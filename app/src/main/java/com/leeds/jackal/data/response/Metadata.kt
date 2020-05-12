package com.leeds.jackal.data.response


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("is_raw_food")
    val isRawFood: Boolean
)