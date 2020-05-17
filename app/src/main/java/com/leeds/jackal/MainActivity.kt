package com.leeds.jackal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var drawer: DrawerLayout? = null
    val mAuth = FirebaseAuth.getInstance()
    private val TAG: String = com.leeds.jackal.MainActivity::class.java.getSimpleName()
    var userRecipes = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var user = mAuth.currentUser

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            Log.v("TAG", " clicked")
            when (id) {
                R.id.nav_home -> {
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

        val savedRecipes = FirebaseDatabase.getInstance().getReference().child("users").child(user!!.uid).child("recipes")

        // Read from the database
        savedRecipes.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<ArrayList<Recipe>>()
//                Log.d(TAG, "Value is: ${value.toString()}")
                for (singleSnapshot in dataSnapshot.children) {
                    var recipe = singleSnapshot.getValue(Recipe::class.java)
                    if (recipe != null) {
                        userRecipes.add(recipe)
                    }
                    Log.d(TAG, recipe.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        Log.d(TAG, "This is the array list")
        Log.d(TAG, userRecipes.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
