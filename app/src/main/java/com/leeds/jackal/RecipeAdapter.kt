package com.leeds.jackal

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.individual_recipe_item.view.*

class RecipeAdapter (
    private val recipes: ArrayList<Recipe>?)
    : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeAdapter.RecipeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val row = layoutInflater.inflate(R.layout.individual_recipe_item, parent,false)
            return RecipeViewHolder(row)
        }

        override fun onBindViewHolder(recipeViewHolder: RecipeViewHolder, i: Int) {
            recipeViewHolder.bindText(recipes!![i].recipeName, recipes[i].serves.toString())


        }


        override fun getItemCount(): Int {
            return if (recipes?.size != null){
                recipes.size
            } else {
                0
            }
        }


        class RecipeViewHolder(v: View) : RecyclerView.ViewHolder(v){

            //        var mTextView: TextView = v.Recipe
            var recipeName: TextView = v.recipe_name
            var serves: TextView = v.serves

            fun bindText(nameText: String, servesText :  String) {
                recipeName.text = nameText
                serves.text = "Serves " + servesText
                //Log.d("RECYCLE Text", recipeText.text.toString())
            }
        }
}
