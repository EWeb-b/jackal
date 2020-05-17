package com.leeds.jackal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leeds.jackal.data.response.Food
import com.leeds.jackal.data.response.NutritionResponse
import kotlinx.android.synthetic.main.nutrition_row.view.*

//private val foods: NutritionResponse

class RecyclerAdapter(private val nutritionResponse: NutritionResponse?): RecyclerView.Adapter<RecyclerAdapter.FoodHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.FoodHolder {
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
//        Glide.with(this).load(food.photo.thumb).into(image)
            Glide.with(view.context).load(food.photo.thumb).into(view.foodImage)
            view.qty.text = food.servingQty.toString()
            view.unit.text = food.servingUnit
            view.name.text = food.foodName
            view.calories.text = food.nfCalories.toString()
            view.weight.text = food.servingWeightGrams.toString()

//            val qty = itemView.findViewById(R.id.qty) as TextView
//            val unit = itemView.findViewById(R.id.unit) as TextView
//            val name = itemView.findViewById(R.id.name) as TextView
//            val calories = itemView.findViewById(R.id.calories) as TextView
//            val weight = itemView.findViewById(R.id.weight) as TextView
//            val foodGroup = itemView.findViewById(R.id.foodGroup) as TextView
//
//            textViewName.text = user.name
//            textViewAddress.text = user.address

        }
    }
}