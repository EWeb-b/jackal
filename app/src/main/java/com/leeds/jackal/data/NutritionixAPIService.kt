package com.leeds.jackal.data

import com.leeds.jackal.data.response.unaggregated.NutritionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val APP_ID = "d372e846"
const val APP_KEY = "7d00e79ba7ea2f64522b0ca53bb5ff5d"

interface NutritionixAPIService {

    @Headers(value = ["Accept: application/json",
                "x-app-id: $APP_ID",
                "x-app-key: $APP_KEY",
                "x-remote-user-id: 0"])
    @POST("natural/nutrients")
    fun getNutrition(@Body request: RecipeRequest): Call<NutritionResponse>
}