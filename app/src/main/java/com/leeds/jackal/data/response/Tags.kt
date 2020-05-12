package com.leeds.jackal.data.response


import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("food_group")
    val foodGroup: Int?,
    val item: String,
    val measure: String?,
    val quantity: String,
    @SerializedName("tag_id")
    val tagId: Int
)