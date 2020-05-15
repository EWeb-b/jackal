package com.leeds.jackal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.leeds.jackal.data.NutritionixAPIService
import com.leeds.jackal.data.RecipeRequest
import com.leeds.jackal.data.response.Food
import com.leeds.jackal.data.response.NutritionResponse
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response

class NutritionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition)
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
        //Toast.makeText(applicationContext,"HELLOOOOO",Toast.LENGTH_LONG).show()

        //val res = retService.getNutrition("2 eggs and 1kg of ham")



//        val responseLiveData:LiveData<Response<NutritionResponse>> = liveData {
//            val response:Response<NutritionResponse> = retService.getNutrition(hello)
//            emit(response)
//        }
//        responseLiveData.observe(this, Observer {
//            val nutritionList:MutableListIterator<Food>? = it.body()?.listIterator()
//            if (nutritionList!=null){
//                while (nutritionList.hasNext()){
//                    val food:Food = nutritionList.next()
//                    Log.i("6969MYTAG4",food.foodName)
//                }
//            }
//
//        })
    }
}
