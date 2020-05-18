package com.leeds.jackal


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leeds.jackal.data.NutritionixAPIService
import com.leeds.jackal.data.RecipeRequest
import com.leeds.jackal.data.response.unaggregated.NutritionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Activity file for the nutrition API.
class NutritionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = RecyclerAdapterNutrition(null)

        // Create an instance of the API interface to interact with and make calls.
        val retService = RetrofitInstance
            .getRetrofitInstance()
            .build()
            .create(NutritionixAPIService::class.java)

        val requestBody =
            RecipeRequest("500g mince beef, 2 carrots, 2 onions, 2 stick celery, 2 500ml tins of chopped tomatoes, 1 tbsp tomato purée, 2 tbsp olive oil, 1 teaspoon oregano, 500g dry spaghetti, 3 cloves garlic")

        val call = retService.getNutrition(requestBody)

        call.enqueue(object : Callback<NutritionResponse> {
            override fun onResponse(call: Call<NutritionResponse>, response: Response<NutritionResponse>) {
                if (response.isSuccessful){
                    // progress_bar.visibility = View.GONE
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@NutritionActivity, RecyclerView.VERTICAL, false)
                        adapter = RecyclerAdapterNutrition(response.body())
                    }
                    Toast.makeText(this@NutritionActivity, response.body()?.foods?.first()?.foodName, Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                Toast.makeText(this@NutritionActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
