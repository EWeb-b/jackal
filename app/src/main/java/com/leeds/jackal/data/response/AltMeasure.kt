package com.leeds.jackal.data.response


import com.google.gson.annotations.SerializedName

data class AltMeasure(
    val measure: String,
    val qty: Int,
    val seq: Int?,
    @SerializedName("serving_weight")
    val servingWeight: Int
)