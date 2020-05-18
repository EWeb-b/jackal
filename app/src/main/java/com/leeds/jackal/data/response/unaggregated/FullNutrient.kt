package com.leeds.jackal.data.response.unaggregated


import com.google.gson.annotations.SerializedName

data class FullNutrient(
    @SerializedName("attr_id")
    val attrId: Int,
    val value: Double
)