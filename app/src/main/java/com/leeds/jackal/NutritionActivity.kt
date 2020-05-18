package com.leeds.jackal


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leeds.jackal.data.NutritionixAPIService
import com.leeds.jackal.data.RecipeRequest
import com.leeds.jackal.data.response.unaggregated.NutritionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

// Activity file for the nutrition API.
class NutritionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        val intent = intent
        val recipeName = intent.getStringExtra("RECIPE_NAME")
        val ingredients = intent.getStringArrayListExtra("INGREDIENTS")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = RecyclerAdapterNutrition(null)

        // Create an instance of the API interface to interact with and make calls.
        val retService = RetrofitInstance
            .getRetrofitInstance()
            .build()
            .create(NutritionixAPIService::class.java)

        val builder = StringBuilder()
        for (i in ingredients) {
            builder.append(i).append(", ")
        }

        val name = findViewById<TextView>(R.id.textView2)
        name.text = recipeName.toString()

        val requestBody = RecipeRequest(builder.toString())
        val call = retService.getNutrition(requestBody)

        call.enqueue(object : Callback<NutritionResponse> {
            override fun onResponse(call: Call<NutritionResponse>, response: Response<NutritionResponse>) {
                if (response.isSuccessful){
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@NutritionActivity, RecyclerView.VERTICAL, false)
                        adapter = RecyclerAdapterNutrition(response.body())
                    }
                }
            }
            override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                Toast.makeText(this@NutritionActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
