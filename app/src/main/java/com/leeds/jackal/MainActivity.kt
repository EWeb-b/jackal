package com.leeds.jackal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var drawer: DrawerLayout? = null
    val mAuth = FirebaseAuth.getInstance()
    private val TAG: String = com.leeds.jackal.MainActivity::class.java.getSimpleName()
    var userRecipes = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecylerView initailisation values
        var recyclerView = findViewById<RecyclerView>(R.id.recipe_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity,RecyclerView.VERTICAL,false)
        recyclerView.adapter = RecipeAdapter(null)
        var user = mAuth.currentUser

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            Log.v("TAG", " clicked")
            when (id) {
                R.id.nav_home -> {
                    val homeIntent =
                        Intent(applicationContext, MainActivity::class.java)
                    startActivity(homeIntent)
                    finish()
                }
                R.id.nav_camera -> {
                    val newRecipeIntent =
                        Intent(applicationContext, PhotoRecipeActivity::class.java)
                    startActivity(newRecipeIntent)
                    finish()
                }
                R.id.nav_help -> {
                }
                R.id.nav_changePassword -> {
                    val changePasswordIntent =
                        Intent(applicationContext, ChangePassword::class.java)
                    startActivity(changePasswordIntent)
                    finish()
                }
                R.id.nav_logOut -> {
                    mAuth.signOut()
                    val logOutIntent =
                        Intent(applicationContext, StartMenu::class.java)
                    startActivity(logOutIntent)
                    finish()
                }
            }
            drawer!!.closeDrawer(GravityCompat.START)
            true
        }

        val burgerMenu =
            findViewById<View>(R.id.burgerMenu) as ImageButton
        burgerMenu.setOnClickListener { drawer!!.openDrawer(GravityCompat.START) }

        val savedRecipes = FirebaseDatabase.getInstance().getReference().child("users").child(user!!.uid).child("recipes")

        // Read from the database
        savedRecipes.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userRecipes = ArrayList<Recipe>()
                for (singleSnapshot in dataSnapshot.children) {
                    var recipe = singleSnapshot.getValue(Recipe::class.java)
                    if (recipe != null) {
                        userRecipes.add(recipe)
                    }
                }
                if (userRecipes.isNotEmpty()){
                    val adapter = RecipeAdapter(userRecipes)
                    recyclerView?.adapter = adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}
