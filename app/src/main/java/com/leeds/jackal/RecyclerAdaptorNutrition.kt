package com.leeds.jackal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leeds.jackal.data.response.unaggregated.Food
import com.leeds.jackal.data.response.unaggregated.NutritionResponse
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_nutrition.view.*
import kotlinx.android.synthetic.main.nutrition_row.view.*

class RecyclerAdapterNutrition(private val nutritionResponse: NutritionResponse?): RecyclerView.Adapter<RecyclerAdapterNutrition.FoodHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterNutrition.FoodHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.nutrition_row, parent, false)
        return FoodHolder(row)
    }

    override fun getItemCount(): Int{
        return if (nutritionResponse?.foods?.size != null){
            nutritionResponse.foods.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        if (nutritionResponse != null) {
            holder.bindItems(nutritionResponse.foods[position])
        }
    }

    class FoodHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(food: Food) {
            Glide.with(view.context).load(food.photo.thumb).into(view.foodImage)
            view.name.text = food.foodName
            view.qty.text = food.servingQty.toString()
            view.unit.text = food.servingUnit
            view.weight.text = food.servingWeightGrams.toString() + "g"
            view.totalFat.text = "Total fat: " + food.nfTotalFat.toString() + "g"
            view.satFat.text = "Sat. Fats: " + food.nfSaturatedFat.toString() + "g"
            view.sodium.text = "Sodium: " + food.nfSodium.toString() + "mg"
            view.carbs.text = "Carbs: " + food.nfTotalCarbohydrate.toString() + "g"
            view.fibre.text =  "Fibre: " + food.nfDietaryFiber.toString() + "g"
            view.sugars.text = "Sugars: " + food.nfSugars.toString() + "g"
            view.protein.text = "Protein: " + food.nfProtein.toString() + "g"
            view.calories.text = "Cals: " + food.nfCalories.toString() + "kcal"
        }
    }
}