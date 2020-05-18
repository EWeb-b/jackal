package com.leeds.jackal

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        private const val BASE_URL ="https://trackapi.nutritionix.com/v2/"
        fun getRetrofitInstance(): Retrofit.Builder {
            val okhttpClientBuilder = OkHttpClient.Builder()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okhttpClientBuilder.build())
        }
    }
}