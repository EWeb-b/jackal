package com.leeds.jackal.data.response.unaggregated


import com.google.gson.annotations.SerializedName

data class AltMeasure(
    val measure: String,
    val qty: Double,
    val seq: Int?,
    @SerializedName("serving_weight")
    val servingWeight: Double
)