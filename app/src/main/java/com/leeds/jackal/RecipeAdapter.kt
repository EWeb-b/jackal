package com.leeds.jackal

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
            recipeViewHolder.bindText(recipes!![i].recipeName, recipes[i].serves.toString(), recipes[i].ingredients)

        }


        override fun getItemCount(): Int {
            return if (recipes?.size != null){
                recipes.size
            } else {
                0
            }
        }


        class RecipeViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener, View.OnLongClickListener {

            var recipeName: TextView = v.recipe_name
            var serves: TextView = v.serves
            lateinit var ingredientList: ArrayList<String>
            var nutritionText: TextView = v.nutritionInfo

            val user = FirebaseAuth.getInstance().currentUser

            init {
                v.setOnClickListener(this)
                v.setOnLongClickListener(this)
            }
            fun bindText(nameText: String, servesText :  String, ingredients: ArrayList<String>) {
                recipeName.text = nameText
                serves.text = "Serves " + servesText
                ingredientList = ingredients
            }

            override fun onClick(p0: View?) {
                Log.d("TAG", "CLICKED!!!!BOOM")
                val context = itemView.context
                val openNutritionActivityIntent =
                    Intent(context, NutritionActivity::class.java)
                openNutritionActivityIntent.putExtra("INGREDIENTS", ingredientList)
                openNutritionActivityIntent.putExtra("RECIPE_NAME", recipeName.text)
                context.startActivity(openNutritionActivityIntent)
            }

            override fun onLongClick(p0: View?): Boolean {
                val context = itemView.context

                val savedRecipes = FirebaseDatabase.getInstance().getReference().child("users").child(user!!.uid).child("recipes")

                // Read from the database
                savedRecipes.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        for (singleSnapshot in dataSnapshot.children) {
                            if (singleSnapshot.child("recipeName").value == recipeName.text){
                                Log.d("To Delete", recipeName.text as String)
                                singleSnapshot.ref.removeValue()
                            }
                        }

                    }
                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                        Log.w("bollocks", "Failed to read value.", error.toException())
                    }
                })
                return true
            }
    }
}
