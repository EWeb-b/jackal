package com.leeds.jackal

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_recylerview_item.view.*

class RecyclerAdapter(
    private val ingredients: ArrayList<String>)
    : RecyclerView.Adapter<RecyclerAdapter.IngredientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.IngredientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.layout_recylerview_item, parent,false)
        return IngredientViewHolder(row)
    }

    override fun onBindViewHolder(ingredientViewHolder: IngredientViewHolder, i: Int) {
        ingredientViewHolder.bindText(ingredients[i])

    }


    override fun getItemCount(): Int {
        return ingredients.size
    }


    class IngredientViewHolder(v: View) : RecyclerView.ViewHolder(v){

//        var mTextView: TextView = v.ingredient
//
        fun bindText(text : String) {
            val ingredientText = itemView.findViewById(R.id.ingredient) as TextView
            ingredientText.setText(text)// = text
            Log.d("RECYCLE Text", ingredientText.text.toString())
        }
    }

}
