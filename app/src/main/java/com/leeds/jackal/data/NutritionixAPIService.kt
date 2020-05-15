package com.leeds.jackal.data

import android.database.Observable
import com.google.gson.JsonObject
import com.leeds.jackal.data.response.NutritionResponse
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val APP_ID = "d372e846"
const val APP_KEY = "7d00e79ba7ea2f64522b0ca53bb5ff5d"

//"https://trackapi.nutritionix.com/v2/natural/nutrients"

//x-remote-user-id:  A unique identifier to represent the end-user who is accessing the Nutritionix API.  If in development mode, set this to 0.  This is used for billing purposes to determine the number of active users your app has
class NutritionRequest(val body: String)


interface NutritionixAPIService {

    @Headers(value = ["Accept: application/json",
                "x-app-id: $APP_ID",
                "x-app-key: $APP_KEY",
                "x-remote-user-id: 0"])
    @POST("natural/nutrients")
    suspend fun getNutrition(@Body request: RecipeRequest): Response<NutritionResponse>
    //Call<NutritionResponse> getNutrition(@Body NutritionRequest request)
//    suspend fun getNutrition(): Response<NutritionResponse>


//    companion object {
//        operator fun invoke(): NutritionixAPIService{
//            val okHttpClient = okHttpClient.Builder()
//                .build()
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("https://trackapi.nutritionix.com/v2/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//        }
//    }
}