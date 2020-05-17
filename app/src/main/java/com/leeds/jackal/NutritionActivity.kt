package com.leeds.jackal

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leeds.jackal.data.NutritionixAPIService
import com.leeds.jackal.data.RecipeRequest
import com.leeds.jackal.data.response.Food
import com.leeds.jackal.data.response.NutritionResponse
import kotlinx.android.synthetic.main.activity_nutrition.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response

class NutritionActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)
        val recyclerView:RecyclerView = findViewById(R.id.recycleView1)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RecyclerAdapter(photosList)
        recyclerView.adapter = adapter

        val retService = RetrofitInstance
            .getRetrofitInstance()
            .build()
            .create(NutritionixAPIService::class.java)

        val requestBody = RecipeRequest("500g mince beef, 2 carrots, 2 onions, 2 stick celery, 2 500ml tins of chopped tomatoes, 1 tbsp tomato purée, 2 tbsp olive oil, 1 teaspoon oregano, 500g dry spaghetti, 3 cloves garlic")

        val resLive: LiveData<Response<NutritionResponse>> = liveData {
            val response = retService.getNutrition(requestBody)
            Log.i("6969response.toString()", response.toString())
            Log.i("6969response.body.toString()", response.body().toString())
            emit(response)
        }
        resLive.observe(this, Observer {
            val name = it.body()?.foods?.first()?.foodName
            Log.i("6969name.toString()", name.toString())
            if (name!=null){Log.i("name", name)}
            Log.i("6969it.message()", it.message())
            Toast.makeText(applicationContext,name,Toast.LENGTH_LONG).show()
        })




        resLive.observe(this, Observer {
            val nutritionList:Iterator<Food>? = it.body()?.foods?.iterator()
            if (nutritionList!=null){
                while (nutritionList.hasNext()){
                    val food = nutritionList.next()
                    Log.i("6969ItFoodName",food.foodName)
                    val image = ImageView(this)
                    Glide.with(this).load(food.photo.thumb).into(image)
                    recyclerView.addView(image)
                }
            }

        })
    }
}
